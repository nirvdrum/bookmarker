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

import java.io.Serializable;
import java.util.*;

import net.negativetwenty.bookmarker.models.*;

import org.apache.tapestry.contrib.tree.model.*;
import org.apache.tapestry.contrib.tree.simple.SimpleTreeDataModel;
import org.apache.tapestry.contrib.tree.simple.SimpleTreeModel;
import org.apache.tapestry.contrib.tree.simple.SimpleTreeStateModel;
import org.apache.tapestry.contrib.tree.simple.TreeNode;

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.conf.Configuration;
import org.objectstyle.cayenne.exp.*;
import org.objectstyle.cayenne.query.SelectQuery;

public class Visit implements Serializable 
{
    protected final DataContext dataContext;
	protected ITreeModel treeModel = null;
	protected List bookmarks = null;
	protected Category category = null;
	protected User user = null;
	
	public Visit() 
	{
		super();
		dataContext = Configuration.getSharedConfiguration()
						.getDomain().createDataContext();
	}

	public DataContext getDataContext() 
	{
		return dataContext;
	}
	
	public boolean isLoggedIn()
	{
	    return (user != null);
	}
	
	public ITreeModel getTreeModel()
	{
	    if (treeModel == null)
	    {
	        final Expression exp = ExpressionFactory.matchExp("parent", null);
	        final SelectQuery query = new SelectQuery(Category.class, exp);
			
	        final List categories = getDataContext().performQuery(query); 
	        final TreeNode rootNode = new TestTreeNode("Bookmarks");
	        
	        buildTree(categories, rootNode);
			
	        final ITreeDataModel treeDataModel = new SimpleTreeDataModel(rootNode);
	        treeModel = new SimpleTreeModel(treeDataModel, new SimpleTreeStateModel());
	    }

	    return treeModel;
	}
	
	protected void buildTree(final List children, final TreeNode root)
	{
	    final Iterator it = children.iterator();
	    while (it.hasNext())
	    {
	        final Category c = (Category) it.next();
	        final TestTreeNode node = new TestTreeNode(c.getName());
	        buildTree(c.getChildren(), node);
	        root.insert(node);
	    }
	}
	
    /**
     * @return Returns the bookmarks.
     */
    public List getBookmarks()
    {
        return bookmarks;
    }
    
    /**
     * @param bookmarks The bookmarks to set.
     */
    public void setBookmarks(final List bookmarks)
    {
        this.bookmarks = bookmarks;
    }
    
    /**
     * @return Returns the category.
     */
    public Category getCategory()
    {
        return category;
    }
    
    /**
     * @param category The category to set.
     */
    public void setCategory(final Category category)
    {
        this.category = category;
    }
    
    public void removeBookmark(final Bookmark b)
    {
        bookmarks.remove(b);
        dataContext.deleteObject(b);
        dataContext.commitChanges();
    }
    
    /**
     * @return Returns the user.
     */
    public User getUser()
    {
        return user;
    }
    
    /**
     * @param user The user to set.
     */
    public void setUser(final User user)
    {
        this.user = user;
    }
}
