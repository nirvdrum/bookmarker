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

import java.awt.event.*;
import javax.swing.*;
import org.jdesktop.jdic.tray.*;

import org.mortbay.jetty.*;
import org.mortbay.http.ajp.*;
import org.mortbay.util.*;

/**
 * This allows for Bookmarker to be used in standalone mode.  It starts up a local instance of Jetty to
 * serve up the Bookmarker webapp.
 * 
 * @author nirvdrum
 */
public class Main
{
    public static void main(String[] args)
    {   
        final Server servletServer = new Server();

        try
        {
            servletServer.addListener(new InetAddrPort(8080));
            servletServer.addListener(new AJP13Listener(new InetAddrPort("127.0.0.1", 8009)));
            servletServer.addWebApplication("/", "bookmarker");
            servletServer.start();
            
            final SysTray st = new SysTray(servletServer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
