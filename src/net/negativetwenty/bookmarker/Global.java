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
 * Created on Oct 27, 2004
 */
package net.negativetwenty.bookmarker;

import java.io.*;
import org.apache.tapestry.engine.IPropertySource;
import org.hsqldb.Server;

/**
 * Handles starting up the local HSQLDB instance.
 * 
 * @author nirvdrum
 */
public class Global implements Serializable
{
    protected final Server server;
    
    public Global(final IPropertySource propertySource)
    {
        // Start up the HSQLDB server if configured to do so in the .application file.
        if(propertySource != null && propertySource.getPropertyValue("start-database-server").equals("true"))
        {
            final String alias = propertySource.getPropertyValue("database-alias");
            final String file  = propertySource.getPropertyValue("database-file");
            final String port  = propertySource.getPropertyValue("database-port");
            
            final String properties = "database.0=file:" + file + ";dbname.0=" + alias;
            
            server = new Server();
            
            server.putPropertiesFromString(properties);
            server.setPort(Integer.parseInt(port));
            server.setLogWriter(new PrintWriter(System.err));
            server.setErrWriter(new PrintWriter(System.err));
           
            new Thread(new Runnable()
            {
                public void run()
                {
                     server.start();
                }
            }).start();
        }
        else
        {
            server = null;
        }
    }
}
