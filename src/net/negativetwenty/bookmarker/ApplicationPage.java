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
 * Created on Jul 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import java.net.URL;
import java.util.*;

import net.negativetwenty.bookmarker.models.*;
import net.negativetwenty.bookmarker.models.Category;

import org.apache.tapestry.*;
import org.apache.tapestry.contrib.tree.model.*;
import org.apache.tapestry.event.*;
import org.apache.tapestry.html.BasePage;
import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.exp.Expression;
import org.objectstyle.cayenne.exp.ExpressionFactory;
import org.objectstyle.cayenne.query.SelectQuery;

import de.nava.informa.core.CategoryIF;
import de.nava.informa.core.ChannelBuilderIF;
import de.nava.informa.core.ChannelExporterIF;
import de.nava.informa.core.ChannelIF;
import de.nava.informa.exporters.*;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.impl.basic.Item;

/**
 * A superclass of all application pages. Contains utility methods
 * to access DataContext and such.
 * 
 * @author Andrei Adamchik
 */
public class ApplicationPage extends BasePage implements PageRenderListener, ITreeStateListener
{
    protected ITreeModel treeModel = null; 
        
    /**
     * Helper method to simplify obtaining Cayenne DataContext by subclasses.
     */
    protected DataContext getDataContext() 
    {
        final Visit visit = (Visit) getVisit();
        return visit.getDataContext();
    }

    /**
     * Helper method to simplify obtaining the currently logged-in user by subclasses.
     */
    protected User getUser() 
    {
        final Visit visit = (Visit) getVisit();
        return visit.getUser();
    }
    
    /**
     * Implementation of PageRenderListener. In Tapestry 3.0 implementing PageRenderListener
     * seems to be the only way to catch the last chance to reinitialize persistent variables
     * before page rendering starts. Default implementation of this method does nothing,
     * allowing subclasses to perform proper initialization.
     */
    public void pageBeginRender(PageEvent event) {}
	
	public void treeStateChanged(TreeStateEvent tse)
	{
	    // Get the selected tree node and grab a copy of the root node.
	    final Visit v = (Visit) getVisit();
	    final TestTreeNode node = (TestTreeNode) v.getTreeModel().getTreeDataModel().getObject(tse.getTreeStateModel().getSelectedNode());
	    final TestTreeNode rootNode = (TestTreeNode) v.getTreeModel().getTreeDataModel().getRoot();
	    
	    // If the node isn't the root, then the user has selected a category.
	    if (node.equals(rootNode) == false)
	    {
	        // Get the full list of categories.
	        final SelectQuery query = new SelectQuery(Category.class);
	        query.addPrefetch("bookmarks");		
	        final List categories = getDataContext().performQuery(query); 
		
	        // Iterate over the category list.
	        final Iterator it = categories.iterator();
	        while (it.hasNext())
	        {
	            final Category c = (Category) it.next();
		    
	            // If the current category matches the selected node . . .
	            if (c.getName().equals(node.getValue()))
	            {
	                // update the visitor with the list of bookmarks and the selected category.
	                v.setBookmarks(c.getBookmarks());
	                v.setCategory(c);
	            }
	        }
	    }
	    
	    // Otherwise, the user has selected the root node.
	    else
	    {
	        // Immediately under the root, we want to show all bookmarks with no associated category.
	        final Expression exp = ExpressionFactory.matchExp("category", null);
	        final SelectQuery query = new SelectQuery(Bookmark.class, exp);
	        
	        // Fetch the list of all such bookmarks and update the visit object appropriately.
	        final List bookmarks = getDataContext().performQuery(query);
            v.setBookmarks(bookmarks);
            final Category c = new Category();
            
            // If there any bookmarks with no associated category, we want to show something under the 
            // category heading for these bookmarks.
	        if (bookmarks.isEmpty() == false)
	        {
	            c.setName("__DEFAULT__");
	        }
            v.setCategory(c);
	    }
	    
        // Redirect to the page used for viewing bookmarks.
        throw new PageRedirectException("ViewBookmarks");
	}
}