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
	private DataContext dataContext = null;
	private ITreeModel treeModel = null;
	private List bookmarks = null;
	private Category category = null;
	
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
	
	public ITreeModel getTreeModel()
	{
	    if (treeModel == null)
	    {
	        Expression exp = ExpressionFactory.matchExp("parent", null);
	        SelectQuery query = new SelectQuery(Category.class, exp);
			
	        List categories = getDataContext().performQuery(query); 
	        TreeNode rootNode = new TestTreeNode("Bookmarks");
	        

	        buildTree(categories, rootNode);
			
	        ITreeDataModel treeDataModel = new SimpleTreeDataModel(rootNode);
	        treeModel = new SimpleTreeModel(treeDataModel, new SimpleTreeStateModel());
	    }
	    
	    return treeModel;
	}
	
	protected void buildTree(List children, TreeNode root)
	{
	    Iterator it = children.iterator();
	    while (it.hasNext())
	    {
	        Category c = (Category) it.next();
	        TestTreeNode node = new TestTreeNode(c.getName());
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
    public void setBookmarks(List bookmarks)
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
    public void setCategory(Category category)
    {
        this.category = category;
    }
}