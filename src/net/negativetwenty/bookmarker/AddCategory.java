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
public abstract class AddCategory extends SecureApplicationPage implements PageRenderListener
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
		
		category.setCreated(new java.util.Date());
		category.setCreatedBy(getUser());
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