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
 * Created on Jan 5, 2005
 */

package net.negativetwenty.bookmarker;

import java.util.Iterator;
import java.util.List;

import net.negativetwenty.bookmarker.models.Bookmark;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.objectstyle.cayenne.access.DataContext;

/**
 * Commits bookmarks decoded from XML to the database.
 *
 * @author nirvdrum
 * @see LoadFile
 */
public abstract class Import extends ApplicationPage
{
    public abstract List getBookmarks();
    public abstract void setBookmarks(List bookmarks);

	/**
	 * Builds up a new CategorySelectionModel for a category drop-down list  (PropertySelection component).
	 * 
	 * @return The new CategorySelectionModel instance.
	 */
	// TODO This is duplicated from AddCategory.  Decide whether this is bad or not.
	public IPropertySelectionModel getCategoryModel()
	{
		return new CategorySelectionModel(getDataContext());
	}
	
	/**
	 * Import bookmarks listener.  This will commit bookmarks decoded from XML to the database.
	 * 
	 * @param cycle
	 */
    public void importBookmarks(final IRequestCycle cycle)
    {
        final Visit v = (Visit) getVisit();
        final DataContext dc = v.getDataContext();
        
        for (final Iterator it = getBookmarks().iterator(); it.hasNext();)
        {
            final Bookmark b = (Bookmark) it.next();
            
            // If the entry isn't being imported, remove it from the data context so it won't be added to the database.
            if (b.isImportEntry() == false)
            {
                dc.deleteObject(b);
            }
        }
        
        // Add the bookmarks to the database.
        dc.commitChanges();
        // TODO This should use a callback.
        cycle.activate("Home");
    }
}
