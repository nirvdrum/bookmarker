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
 * Created on Dec 22, 2004
 */

package net.negativetwenty.bookmarker;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import net.negativetwenty.bookmarker.models.Bookmark;
import net.negativetwenty.bookmarker.models.User;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.request.IUploadFile;
import org.objectstyle.cayenne.CayenneException;
import org.objectstyle.cayenne.util.XMLDecoder;

/**
 * Loads an XML file containing exported bookmarks and reconstructs the Bookmark objects from it.
 *
 * @author nirvdrum
 * @see Import
 */
public abstract class LoadFile extends SecureApplicationPage
{
    public abstract IUploadFile getFile();
    
    /**
     * Submit listener.  Handles the loading of an XML file for import.
     * 
     * @param cycle
     */
    public void submit(final IRequestCycle cycle)
    {
        final Visit v = (Visit) getVisit();
        final IUploadFile file = getFile();
        
        // If a file wasn't loaded, show the form again.
        if (null == file)
        {
            return;
        }
        
        try
        {
            // Set up the XML decoder.
            final Reader r = new InputStreamReader(file.getStream());
            final XMLDecoder decoder = new XMLDecoder();
            
            // Actually decode the bookmarks.
            final List bookmarks = decoder.decodeCollection(r);
            final User user = v.getUser();
            
            // For each decoded bookmark, register it with a data context and set its "createdBy" to the currently logged-in user.
            for (final Iterator it = bookmarks.iterator(); it.hasNext();)
            {
                final Bookmark b = (Bookmark) it.next();
                user.getDataContext().registerNewObject(b);
                b.setCreatedBy(user);
            }
            
            // Redirect to the Import page, which will allow the user to pick which bookmarks to actually import.
            Import page = (Import) cycle.getPage("Import");
            page.setBookmarks(bookmarks);
            cycle.activate(page);
        }
        catch (CayenneException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
