/*
 * Created on Jul 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import java.net.*;
import java.util.*;

import net.negativetwenty.bookmarker.models.Bookmark;
import net.negativetwenty.bookmarker.models.Category;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.form.IPropertySelectionModel;
import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.query.SelectQuery;

import de.nava.informa.core.*;
import de.nava.informa.impl.basic.*;
import de.nava.informa.exporters.*;

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
		b.setClickCount(new Integer(0));
		
		dc.registerNewObject(b);
		b.setCategory(getCategory());
		
		dc.commitChanges();
		
		createRdf();
		
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
	
	public void createRdf()
	{
	    DataContext dc = getDataContext();
        SelectQuery query = new SelectQuery(Bookmark.class);
        List bookmarks = getDataContext().performQuery(query); 
	    
	    try
	    {
	        ChannelBuilderIF builder = new ChannelBuilder();
	        ChannelIF channel = builder.createChannel("Bookmarks");
	        channel.setDescription("Test Channel: " + "Bookmarks");
	        
	        Iterator it = bookmarks.iterator();
	        while (it.hasNext())
	        {
	            Bookmark b = (Bookmark) it.next();
	            
	            Item item = new Item(b.getTitle(), b.getDescription(), new URL(b.getUrl()));
	            
	            // TODO When I finally force Bookmarks to have some category, the category name should never be null.
	            if (b.getCategory() != null)
	            {
	                CategoryIF category = new de.nava.informa.impl.basic.Category(b.getCategory().getName());
	                channel.addCategory(category);
	                item.addCategory(category);
	            }
	            
	            // TODO Update this to whatever the logged in username is.
	            item.setCreator("nirvdrum");
	            
	            channel.addItem(item);
	        }

	        String rdffile = getRequestCycle().getRequestContext().getServlet().getServletContext().getRealPath(getComponent("border").getAsset("rdffile").getResourceLocation().getPath());
	        ChannelExporterIF exporter = new RSS_2_0_Exporter(rdffile);
	        exporter.write(channel);
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
}
