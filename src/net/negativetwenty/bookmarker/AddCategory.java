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
 */
package net.negativetwenty.bookmarker;

import net.negativetwenty.bookmarker.models.*;

import org.apache.tapestry.*;
import org.apache.tapestry.event.*;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.objectstyle.cayenne.access.DataContext;


/**
 * Controls adding new categories to the database.
 * 
 * @author nirvdrum
 */
public abstract class AddCategory extends SecureApplicationPage implements PageRenderListener
{
	public abstract Category getCategory();
	public abstract void setCategory(Category category);
	public abstract Category getParent();
	public abstract void setParent(Category parent);
	
	/**
	 * Builds up a new CategorySelectionModel for a category drop-down list  (PropertySelection component).
	 * 
	 * @return The new CategorySelectionModel instance.
	 */
	public IPropertySelectionModel getCategoryModel()
	{
		return new CategorySelectionModel(getDataContext());
	}
	
	/**
	 * AddCategory listener.  Adds a new category to the database.
	 * 
	 * @param cycle
	 */
	public void addCategory(final IRequestCycle cycle)
	{
	    final Visit v = (Visit) getVisit();
	    final DataContext dc = getDataContext();
	    final Category category = getCategory();
		
	    // Register the category with the dc.
	    dc.registerNewObject(category);
	    
	    // Set the category's property values.
	    category.setCreated(new java.util.Date());
	    category.setCreatedBy(getUser());
	    category.setParent(getParent());
		
	    // Add the category to the db.
	    dc.commitChanges();
		
		// Invalidate the tree model so the new category will be added to the tree.
		v.invalidateTreeModel();
		
		// TODO Change this to be some sort of callback.
		cycle.activate("Home");
	}
	
	/**
	 * Make sure the category property is a valid Category instance when the page renders.
	 * 
	 * @param event
	 */
	public void pageBeginRender(final PageEvent event)
	{
		if (getCategory() == null)
		{
			setCategory(new Category());
		}
	}
}