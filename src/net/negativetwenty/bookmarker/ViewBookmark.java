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
 * Created on Jan 6, 2005
 */

package net.negativetwenty.bookmarker;

import net.negativetwenty.bookmarker.models.Bookmark;

import org.apache.tapestry.IExternalPage;
import org.apache.tapestry.IRender;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.RedirectException;
import org.objectstyle.cayenne.DataObjectUtils;
import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.conf.Configuration;

/**
 * Represents a bookmarkable page that can be used to access each of the Bookmarks.  Its primary functionality is to fetch a Bookmark,
 * increment its click count, and then redirect the browser to the Bookmark's URL. 
 *
 * @author nirvdrum
 */
public class ViewBookmark extends ApplicationPage implements IExternalPage, IRender
{
    /**
     * Handles fetching the Bookmark, incrementing its click count, and redirect the browser to the Bookmark's URL.
     * 
     * @see org.apache.tapestry.IExternalPage#activateExternalPage(java.lang.Object[], org.apache.tapestry.IRequestCycle)
     */
    public void activateExternalPage(Object[] parameters, IRequestCycle cycle)
    {
        Bookmark b = null;
        try
        {
            // Fetch the Bookmark and increment its click count.
            final DataContext dc = Configuration.getSharedConfiguration().getDomain().createDataContext();
            final Integer id = (Integer) parameters[0];
            b = (Bookmark) DataObjectUtils.objectForPK(dc, Bookmark.class, id.intValue());
            b.addClick();
        }
        catch (Exception e)
        {
            // In case anything goes wrong (e.g., an invalid id is specified in the URL), send the user to the home page.
            cycle.activate("Home");
        }
        
        // Redirect to the Bookmark's URL.
        throw new RedirectException(b.getUrl());
    }
}
