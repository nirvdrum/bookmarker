/*
 * Created on Jul 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import java.util.*;

import net.negativetwenty.bookmarker.models.Category;

import org.apache.tapestry.*;
import org.apache.tapestry.contrib.tree.model.*;
import org.apache.tapestry.event.*;
import org.apache.tapestry.html.BasePage;
import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.query.SelectQuery;

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
        Visit visit = (Visit) getVisit();
        return visit.getDataContext();
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
	    Visit v = (Visit) getVisit();
	    TestTreeNode node = (TestTreeNode) v.getTreeModel().getTreeDataModel().getObject(tse.getTreeStateModel().getSelectedNode());
    
		SelectQuery query = new SelectQuery(Category.class);
		query.addPrefetch("bookmarks");
			
		List categories = getDataContext().performQuery(query); 
		
		Iterator it = categories.iterator();
		while (it.hasNext())
		{
		    Category c = (Category) it.next();
		    
		    if (c.getName().equals(node.getValue()))
		    {
		        v.setBookmarks(c.getBookmarks());
		        v.setCategory(c);
		        
		        throw new PageRedirectException("ViewBookmarks");
		    }
		}
	}
}