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
	
	public CategorySelectionModel(final DataContext dataContext)
	{
	    final SelectQuery query = new SelectQuery(Category.class);
		
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
	public Object getOption(final int index) 
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
	public String getLabel(final int index) 
	{
		if (index == 0)
		{
			return (String) categories.get(index);
		}
		
		final Category c = (Category) categories.get(index);	
		return c.getName();
	}

	/* (non-Javadoc)
	 * @see org.apache.tapestry.form.IPropertySelectionModel#getValue(int)
	 */
	public String getValue(final int index) 
	{
		return Integer.toString(index);
	}

	/* (non-Javadoc)
	 * @see org.apache.tapestry.form.IPropertySelectionModel#translateValue(java.lang.String)
	 */
	public Object translateValue(final String value) 
	{
	    final int index = Integer.parseInt(value);
		
		return getOption(index);
	}
}
