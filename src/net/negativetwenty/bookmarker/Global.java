/*
 * Created on Oct 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import java.io.*;
import org.apache.tapestry.engine.IPropertySource;
import org.hsqldb.Server;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Global implements Serializable
{
    protected final Server server;
    
    public Global(final IPropertySource propertySource)
    {
        // Start up the HSQLDB server if configured to do so in the .application file.
        if(propertySource != null && propertySource.getPropertyValue("start-database-server").equals("true"))
        {
            String alias = propertySource.getPropertyValue("database-alias");
            String file  = propertySource.getPropertyValue("database-file");
            String port  = propertySource.getPropertyValue("database-port");
            
            String properties = "database.0=file:" + file + ";dbname.0=" + alias;
            
            System.out.println("Database properties = " + properties);
            
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
