/*
 * Created on Jul 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import net.negativetwenty.bookmarker.models.*;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.objectstyle.cayenne.access.DataContext;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AddBookmark extends SecureApplicationPage 
{
	public abstract Bookmark getBookmark();
	public abstract void setBookmark(Bookmark bookmark);
	public abstract Category getCategory();
	public abstract void setCategory(Category category);
	
	public IPropertySelectionModel getCategoryModel()
	{
		return new CategorySelectionModel(getDataContext());
	}
	
	public void addBookmark(IRequestCycle cycle)
	{
		DataContext dc = getDataContext();
		Bookmark b = getBookmark();
		
		dc.registerNewObject(b);
		b.setCategory(getCategory());
		
		dc.commitChanges();
		
	    cycle.activate("Home");
	}
	
	public void pageBeginRender(PageEvent event)
	{	
		if (getBookmark() == null)
		{
			setBookmark(new Bookmark());
		}
		
		if (getCategory() == null)
		{
			setCategory(new Category());
		}
	}
}
