/*
 * Created on Jul 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import net.negativetwenty.bookmarker.models.*;

import java.util.*;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.objectstyle.cayenne.query.*;
import org.objectstyle.cayenne.access.DataContext;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CategorySelectionModel implements IPropertySelectionModel 
{
	List categories = null;
	
	public CategorySelectionModel(DataContext dataContext)
	{
		SelectQuery query = new SelectQuery(Category.class);
		
		categories = new ArrayList();
		categories.add("N/A");
		categories.addAll(dataContext.performQuery(query));
	}
	
	/* (non-Javadoc)
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getOptionCount()
	 */
	public int getOptionCount() 
	{
		return categories.size();
	}

	/* (non-Javadoc)
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getOption(int)
	 */
	public Object getOption(int index) 
	{
		if (index == 0)
		{
			return null;
		}
	
		return categories.get(index);
	}

	/* (non-Javadoc)
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getLabel(int)
	 */
	public String getLabel(int index) 
	{
		if (index == 0)
		{
			return (String) categories.get(index);
		}
		
		Category c = (Category) categories.get(index);	
		return c.getName();
	}

	/* (non-Javadoc)
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getValue(int)
	 */
	public String getValue(int index) 
	{
		return Integer.toString(index);
	}

	/* (non-Javadoc)
	 * @see org.apache.tapestry.form.IPropertySelectionModel#translateValue(java.lang.String)
	 */
	public Object translateValue(String value) 
	{
		int index = Integer.parseInt(value);
		
		return getOption(index);
	}
}
