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

import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.conf.Configuration;

public class Visit implements Serializable 
{
	private DataContext dataContext = null;
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
}
