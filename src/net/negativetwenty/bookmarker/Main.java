/*
 * Created on Oct 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import org.mortbay.jetty.*;
import org.mortbay.util.*;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Main
{
    public static void main(String[] args)
    {           
        Server servletServer = new Server();
        
        try
        {
            System.err.println(servletServer.getConfiguration());
            servletServer.addListener(new InetAddrPort("127.0.0.1", 8080));
            servletServer.addWebApplication("/", "bookmarker.war");
            servletServer.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
