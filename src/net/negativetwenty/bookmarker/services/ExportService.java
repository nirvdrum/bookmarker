/*
 * Created on Dec 17, 2004
 *
 * TODO Insert license or copyright statement.
 */

package net.negativetwenty.bookmarker.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;

import net.negativetwenty.bookmarker.Visit;
import net.negativetwenty.bookmarker.models.Bookmark;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.AbstractService;
import org.apache.tapestry.engine.IEngineServiceView;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.request.ResponseOutputStream;
import org.objectstyle.cayenne.query.SelectQuery;
import org.objectstyle.cayenne.util.XMLEncoder;

/**
 * TODO Briefly describe the class or interface. 
 *
 * @author nirvdrum
 */
public class ExportService extends AbstractService
{
    public static final String SERVICE_NAME = "bookmarker.export";
    
    /* (non-Javadoc)
     * @see org.apache.tapestry.engine.IEngineService#getName()
     */
    public String getName()
    {
        return SERVICE_NAME;
    }

    /* (non-Javadoc)
     * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.engine.IEngineServiceView, org.apache.tapestry.IRequestCycle, org.apache.tapestry.request.ResponseOutputStream)
     */
    public void service(IEngineServiceView engine, IRequestCycle cycle,
            ResponseOutputStream output) throws ServletException, IOException
    {
        final Visit v = (Visit) cycle.getEngine().getVisit(cycle);      
        final SelectQuery query = new SelectQuery(Bookmark.class);
        final List bookmarks = v.getDataContext().performQuery(query); 

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XMLEncoder encoder = new XMLEncoder(new PrintWriter(out, true));
        encoder.encodeCollection("Bookmarks", bookmarks);
        encoder.getPrintWriter();
        
        //cycle.getRequestContext().getResponse().setHeader(
        //       "Content-Disposition",
        //        "attachment; filename=bookmarker_export.xml");
        output.setContentType("application/octet-stream");
        output.write(out.toByteArray());
    }

    /* (non-Javadoc)
     * @see org.apache.tapestry.engine.IEngineService#getLink(org.apache.tapestry.IRequestCycle, org.apache.tapestry.IComponent, java.lang.Object[])
     */
    public ILink getLink(IRequestCycle cycle, IComponent component, Object[] parameters)
    {
        return this.constructLink(cycle, SERVICE_NAME, null, parameters, true);        
    }

}
