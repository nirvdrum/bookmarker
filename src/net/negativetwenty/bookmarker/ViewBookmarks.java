/*
 *    Copyright 2004, Kevin J. Menard, Jr.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*
 * Created on Jul 24, 2004
 */
package net.negativetwenty.bookmarker;

import net.negativetwenty.bookmarker.models.*;

import org.apache.tapestry.*;
import org.apache.tapestry.event.*;
import org.objectstyle.cayenne.*;


/**
 * Handles interaction with Bookmark views.
 * 
 * @author nirvdrum
 */
public class ViewBookmarks extends ApplicationPage
{
    /**
     * LinkClicked listener.  Reacts to a Bookmark's URL being clicked.
     * 
     * @param cycle
     */
    // TODO This should delegate to the "ViewBookmark" page to remove duplicated work.
    public void linkClicked(final IRequestCycle cycle)
    {
        // Increment the Bookmark's click count.
        final ObjectId id = (ObjectId) cycle.getServiceParameters()[0];
        final Bookmark b = (Bookmark) DataObjectUtils.objectForPK(getDataContext(), id);
        b.addClick();

        // Redirect to the Bookmark's URL.
        throw new RedirectException(b.getUrl());
    }

    /**
     * RemoveBookmark listener.  Removes a bookmark from the database.
     * 
     * @param cycle
     */
    public void removeBookmark(final IRequestCycle cycle)
    {
        // Fetch the bookmark from the database, given its ObjectId.
        final Visit v = (Visit) getVisit();
        final ObjectId id = (ObjectId) cycle.getServiceParameters()[0];
        final Bookmark b = (Bookmark) DataObjectUtils.objectForPK(getDataContext(), id);
        
        // Remove the Bookmark from the database.
        v.removeBookmark(b);
        
        // TODO This seems pretty bad.  The RDF file creation should probably move into a new service or something.
        // Update the RDF file.
        final AddBookmark ab = (AddBookmark) cycle.getPage("AddBookmark");
        ab.createRdf(cycle);
    }
    
    /**
     * ModifyBookmark listener.  Sets things up for a Bookmark to be modified.
     * 
     * @param cycle
     */
    public void modifyBookmark(final IRequestCycle cycle)
    {
        // Fetch the bookmark from the database, given its ObjectId.
        final ObjectId id = (ObjectId) cycle.getServiceParameters()[0];
        final Bookmark b = (Bookmark) DataObjectUtils.objectForPK(getDataContext(), id);
        
        // Set up an instance of the AddBookmark page for bookmark modification, and redirect to that page.
        final AddBookmark ab = (AddBookmark) cycle.getPage("AddBookmark");
        ab.setBookmark(b);
        cycle.activate(ab);
    }
    
    /**
     * Set the heading of the page to be the currently selected category.
     * 
     * @param event
     */
    public void pageBeginRender(final PageEvent event) 
    {
        final Visit v = (Visit) getVisit();
        final Category c = v.getCategory();
        
        if (null != c)
        {
            getComponent("border").setProperty("heading", c.getName());
        }
    }
}
