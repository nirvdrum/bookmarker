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
 */
package net.negativetwenty.bookmarker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Level;
import org.apache.tapestry.ApplicationServlet;
import org.objectstyle.cayenne.conf.BasicServletConfiguration;
import org.objectstyle.cayenne.conf.Configuration;

/**
 * Main servlet for the Bookmarker webapp.
 *
 * @author nirvdrum
 */
public class BookmarkerServlet extends ApplicationServlet 
{
    /**
     * Takes care of setting up Cayenne.
     * 
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