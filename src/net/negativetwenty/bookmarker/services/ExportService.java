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
 * Created on Dec 17, 2004
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
 * Exports bookmarks from the database to an XML file.
 *
 * @author nirvdrum
 */
public class ExportService extends AbstractService
{
    public static final String SERVICE_NAME = "bookmarker.export";
    
    /**
     * @see org.apache.tapestry.engine.IEngineService#getName()
     */
    public String getName()
    {
        return SERVICE_NAME;
    }

    /**
     * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.engine.IEngineServiceView, org.apache.tapestry.IRequestCycle, org.apache.tapestry.request.ResponseOutputStream)
     */
    public void service(IEngineServiceView engine, IRequestCycle cycle,
            ResponseOutputStream output) throws ServletException, IOException
    {
        // Get all of the bookmarks in the database.
        final Visit v = (Visit) cycle.getEngine().getVisit(cycle);      
        final SelectQuery query = new SelectQuery(Bookmark.class);
        final List bookmarks = v.getDataContext().performQuery(query); 

        // Encode the list of bookmarks to XML.
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final XMLEncoder encoder = new XMLEncoder(new PrintWriter(out, true));
        encoder.encodeCollection("Bookmarks", bookmarks);
        encoder.getPrintWriter();
        
        // Write the XML to the output stream sent to the web browser.
        cycle.getRequestContext().getResponse().setHeader(
               "Content-Disposition",
               "attachment; filename=bookmarker_export.xml");
        output.setContentType("application/octet-stream");
        output.write(out.toByteArray());
    }

    /**
     * @see org.apache.tapestry.engine.IEngineService#getLink(org.apache.tapestry.IRequestCycle, org.apache.tapestry.IComponent, java.lang.Object[])
     */
    public ILink getLink(IRequestCycle cycle, IComponent component, Object[] parameters)
    {
        return this.constructLink(cycle, SERVICE_NAME, null, parameters, true);        
    }

}
