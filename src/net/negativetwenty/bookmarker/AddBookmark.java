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

import java.net.*;
import java.util.*;

import net.negativetwenty.bookmarker.models.*;
import net.negativetwenty.bookmarker.models.Category;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.query.SelectQuery;

//import de.nava.informa.core.*;
//import de.nava.informa.impl.basic.*;
//import de.nava.informa.exporters.*;

/**
 * Controls adding a new bookmark to the database.
 * 
 * @author nirvdrum
 */
public abstract class AddBookmark extends SecureApplicationPage 
{
	public abstract Bookmark getBookmark();
	public abstract void setBookmark(Bookmark bookmark);
	public abstract Category getCategory();
	public abstract void setCategory(Category category);
	
	/**
	 * Builds up a new CategorySelectionModel for a category drop-down list  (PropertySelection component).
	 * 
	 * @return The new CategorySelectionModel instance.
	 */
	// TODO This is duplicated from AddCategory.  Decide whether this is bad or not.
	public IPropertySelectionModel getCategoryModel()
	{
		return new CategorySelectionModel(getDataContext());
	}
	
	/**
	 * AddBookmark listener.  Adds a new bookmark to or modifies an existing bookmark in the database.
	 * 
	 * @param cycle
	 */
	// TODO This should be refactored to be a different name, since it is also used to modify existing bookmarks.
	public void addBookmark(final IRequestCycle cycle)
	{
		final DataContext dc = getDataContext();
		final Bookmark b = getBookmark();
		
		// If this is a new bookmark (an existing one -- indicating a modification -- would not have a null objectID) . . .
		if (b.getObjectId() == null)
		{
		    // register the object with the data context . . .
		    dc.registerNewObject(b);
		    
		    // and initialize some parameters.
		    b.setClickCount(new Integer(0));
		    b.setCreated(new Date());
		    b.setCreatedBy(getUser());
		}
		
		// Set the bookmark's category and commit it to the db.
		b.setCategory(getCategory());
		dc.commitChanges();
		
		// Reset the bookmark object so future requests don't think we're trying to modify an existing bookmark.
		setBookmark(null);
		
		// Update the RDF file.
		createRdf();
		
		// TODO This should probably be a callback, so one could add multiple bookmarks to the same category without a bunch of clicking.
		// Go back to the home page.
	    cycle.activate("Home");
	}
	
	/**
	 * Make sure the page has a valid Bookmark and Category to work with.
	 * 
	 * @param event
	 */
	public void pageBeginRender(final PageEvent event)
	{	
	    final Visit v = (Visit) getVisit();
	    
	    // If the bookmark object is null, create a new one.
		if (getBookmark() == null)
		{
			setBookmark(new Bookmark());
		}
		
		// If the category object is null . . .
		if (getCategory() == null)
		{
		    // try to set it to the category stored in the visit object.
		    // This corresponds to clicking "Add Bookmark" from a page with a list of a given category's bookmarks.
		    if (v.getCategory() != null)
		    {
		        setCategory(v.getCategory());
		    }
		    
		    // Otherwise, just give it a new category.  This will show as "N/A" in the category selection list in the HTML page.
		    else
		    {
		        setCategory(new Category());
		    }
		}
	}
	
	/**
	 * Create a new RDF file to reflect changes made to the database.
	 */
	public void createRdf()
	{
	    /*
	    // Get a list of all the bookmarks.
        final SelectQuery query = new SelectQuery(Bookmark.class);
        final List bookmarks = getDataContext().performQuery(query);
        final Visit v = (Visit) getVisit();
	    
	    try
	    {
	        // Set things up for creating the RDF file.
	        final ChannelBuilderIF builder = new ChannelBuilder();
	        final ChannelIF channel = builder.createChannel("Bookmarks");
	        channel.setDescription("Test Channel: " + "Bookmarks");
	        
	        // For each bookmark . . .
	        final Iterator it = bookmarks.iterator();
	        while (it.hasNext())
	        {
	            final Bookmark b = (Bookmark) it.next();
	            
	            // create an item for the RDF file . . .
	            final Item item = new Item(b.getTitle(), b.getDescription(), new URL(b.getUrl()));
	            
	            // TODO When I finally force Bookmarks to have some category, the category name should never be null.
	            // if the bookmark has an associated category, create that category for the item in RDF . . .
	            if (b.getCategory() != null)
	            {
	                final CategoryIF category = new de.nava.informa.impl.basic.Category(b.getCategory().getName());
	                channel.addCategory(category);
	                item.addCategory(category);
	            }
	            
	            // Update a few other RDF properties.
	            item.setCreator(v.getUser().getUsername());
	            item.setDate(b.getCreated());
	            
	            // Add the RDF item to the RDF output.
	            channel.addItem(item);
	        }

	        // Actually create the RDF file now.
	        final String rdffile = getRequestCycle().getRequestContext().getServlet().getServletContext().getRealPath(getComponent("border").getAsset("rdffile").getResourceLocation().getPath());
	        final ChannelExporterIF exporter = new RSS_2_0_Exporter(rdffile);
	        exporter.write(channel);
	    }
	    catch (final Exception e)
	    {
	        e.printStackTrace();
	    }*/
	}
}
