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
 */
package net.negativetwenty.bookmarker;

import java.io.*;
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

/**
 * Represents session state variables.
 *
 * @author nirvdrum
 */
public class Visit implements Serializable 
{
    protected final DataContext dataContext;
	protected ITreeModel treeModel = null;
	protected List bookmarks = null;
	protected Category category = null;
	protected User user = null;
	
	/**
	 * Default constructor.
	 */
	public Visit() 
	{
		super();
		dataContext = Configuration.getSharedConfiguration()
						.getDomain().createDataContext();
	}

	/**
	 * Returns a DataContext for use in database operations.
	 * 
	 * @return The DataContext instance.
	 */
	public DataContext getDataContext() 
	{
		return dataContext;
	}
	
	/**
	 * Indicates whether a user is logged-in.
	 * 
	 * @return true = User is logged in.
	 */
	public boolean isLoggedIn()
	{
	    return (user != null);
	}
	
	/**
	 * Invalidates the model used for the category tree view.
	 */
	public void invalidateTreeModel()
	{
	    treeModel = null;
	}
	
	/**
	 * Returns an instance of the model used to for the category tree view, creating a new instance if
	 * necessary.
	 * 
	 * @return The model for the category tree view.
	 */
	public ITreeModel getTreeModel()
	{
	    // If the model is null, create a new one.
	    if (treeModel == null)
	    {
	        // Get all the categories that don't have a parent category.
	        final Expression exp = ExpressionFactory.matchExp("parent", null);
	        final SelectQuery query = new SelectQuery(Category.class, exp);
	        final List categories = getDataContext().performQuery(query); 
	        
	        // Build the tree recursively, starting with the categories that have no parent category.
	        final TreeNode rootNode = new TestTreeNode("Bookmarks");
	        buildTree(categories, rootNode);
			
	        // Build the tree model out of the tree.
	        final ITreeDataModel treeDataModel = new SimpleTreeDataModel(rootNode);
	        treeModel = new SimpleTreeModel(treeDataModel, new SimpleTreeStateModel());
	    }

	    return treeModel;
	}
	
	/**
	 * Recursively builds a tree into root of all categories and their child categories.
	 * 
	 * @param children The list of categories to build recursively on.
	 * @param root The root node of a sub tree of categories.
	 */
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
     * Returns the active list of bookmarks.
     * 
     * @return The active list of bookmarks.
     */
    public List getBookmarks()
    {
        return bookmarks;
    }
    
    /**
     * Sets the active list of bookmarks.
     * 
     * @param bookmarks The list of active bookmarks.
     */
    public void setBookmarks(final List bookmarks)
    {
        this.bookmarks = bookmarks;
    }
    
    /**
     * Returns the active category.
     * 
     * @return Returns The active category.
     */
    public Category getCategory()
    {
        return category;
    }
    
    /**
     * Sets the active category.
     * 
     * @param category The active category.
     */
    public void setCategory(final Category category)
    {
        this.category = category;
    }
    
    /**
     * Removes a bookmark from the database.
     * 
     * @param b The bookmark to remove.
     */
    public void removeBookmark(final Bookmark b)
    {
        bookmarks.remove(b);
        dataContext.deleteObject(b);
        dataContext.commitChanges();
    }
    
    /**
     * Returns the logged-in user.
     * 
     * @return The logged-in user..
     */
    public User getUser()
    {
        return user;
    }
    
    /**
     * Sets the logged-in user.
     * 
     * @param user The logged-in user.
     */
    public void setUser(final User user)
    {
        this.user = user;
    }
}
