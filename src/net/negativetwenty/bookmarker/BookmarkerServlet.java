/*
 * Created on Jul 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Level;
import org.apache.tapestry.ApplicationServlet;
import org.objectstyle.cayenne.conf.BasicServletConfiguration;
import org.objectstyle.cayenne.conf.Configuration;

public class BookmarkerServlet extends ApplicationServlet 
{
   // Server server = null;
    
    /**
     * @see javax.servlet.Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException 
	{
        super.init(config);
        
        Configuration.bootstrapSharedConfiguration(net.negativetwenty.bookmarker.Visit.class);
        Configuration.setLoggingLevel(Level.WARN);
        Configuration.configureCommonLogging();
        BasicServletConfiguration.initializeConfiguration(config.getServletContext());
    }
}