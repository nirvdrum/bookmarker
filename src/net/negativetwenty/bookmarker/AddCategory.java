/*
 * Created on Jul 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import net.negativetwenty.bookmarker.models.*;

import org.apache.tapestry.*;
import org.apache.tapestry.event.*;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.objectstyle.cayenne.access.DataContext;


/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AddCategory extends ApplicationPage implements PageRenderListener
{
	public abstract Category getCategory();
	public abstract void setCategory(Category category);
	public abstract Category getParent();
	public abstract void setParent(Category parent);
	
	public IPropertySelectionModel getCategoryModel()
	{
		return new CategorySelectionModel(getDataContext());
	}
	
	public void addCategory(IRequestCycle cycle)
	{
		DataContext dc = getDataContext();
		Category category = getCategory();
		
		dc.registerNewObject(category);
		category.setParent(getParent());
		
		dc.commitChanges();
		
		cycle.activate("Home");
	}
	
	public void pageBeginRender(PageEvent event)
	{
		if (getCategory() == null)
		{
			setCategory(new Category());
		}
	}
}