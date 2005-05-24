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
 * Created on Oct 22, 2004
 */
package net.negativetwenty.bookmarker.components;

import java.util.*;

import net.negativetwenty.bookmarker.AddBookmark;
import net.negativetwenty.bookmarker.Login;
import net.negativetwenty.bookmarker.Visit;
import net.negativetwenty.bookmarker.models.Category;

import org.apache.tapestry.*;
import org.apache.tapestry.callback.PageCallback;
import org.objectstyle.cayenne.DataObjectUtils;

/**
 * The Border class provides the logic for the Border component.
 * 
 * @author nirvdrum
 */
public class Border extends BaseComponent
{    
    /**
     * Login listener.  Records the current page being visited and redirects the user to the Login page.
     * 
     * @param cycle
     */
    public void login(final IRequestCycle cycle)
    {
        final Login login = (Login) cycle.getPage("Login");
        login.setCallback(new PageCallback(getPage()));
        throw new PageRedirectException(login);
    }
    
    /**
     * Logout listener.  Logs a user out of the system.
     * 
     * @param cycle
     */
    public void logout(final IRequestCycle cycle)
    {
        final Visit v = (Visit) getPage().getVisit();
        v.setUser(null);
        cycle.activate("Home");
    }
    
    /**
     * AddBookmark listener.  Redirects the user to the AddBookmark page, making sure to clear any cached
     * Bookmark values.
     * 
     * @param cycle
     */
    public void addBookmark(final IRequestCycle cycle)
    {
        final AddBookmark ab = (AddBookmark) cycle.getPage("AddBookmark");
        ab.setBookmark(null);
        cycle.activate(ab);
    }
    
    /**
     * ViewBookmarks listener.  Shows all the bookmarks for the selected category.  Also expands the category
     * tree as appropriate.
     * 
     * @param cycle
     */
    public void viewBookmarks(final IRequestCycle cycle)
    {
        // Retrieve the Visit object for several operations.
        final Visit v = (Visit) getPage().getVisit();
        
        // Restore the Category object passed as a parameter in the link.
        final Integer id = (Integer) cycle.getServiceParameters()[0];
        final Category c = (Category) DataObjectUtils.objectForPK(v.getDataContext(), Category.class, id.intValue());
        
        // Update the clicked category in the Visit object so ViewBookmarks has everything it needs.
        v.setCategory(c);
        
        // TODO The following should only be done if the category has subcategories, otherwise we're wasting space in the set.  For now though, the check requires a costly DB access so it is not performed.
        // Add the Category object to the tree state so that it expands node (similar to clicking the '+' button in the tree).
        final Set treeState = v.getTreeState();
        treeState.add(new Integer(id));
        
        // Redirect to the ViewBookmarks page to view the bookmarks in this category.
        cycle.activate("ViewBookmarks");
    }
}
