/*
 * Created on Mar 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;
import org.mortbay.jetty.Server;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SysTray implements ActionListener
{
	public static final String SHOW_MENU = "Show Menu";
	public static final String START_SERVER = "Start Bookmarker";
	public static final String STOP_SERVER = "Stop Bookmarker";
	public static final String QUIT = "Quit Bookmarker";
	
	protected final Server servletServer;
	protected final JMenuItem startServerItem;
	protected final JMenuItem stopServerItem;
	protected final JMenuItem quitItem;
	
	public SysTray(final Server servletServer)
	{	
		this.servletServer = servletServer;
		
        final JPopupMenu menu = new JPopupMenu("Tray Icon Menu");
        
        startServerItem = new JMenuItem(START_SERVER);
        startServerItem.setActionCommand(START_SERVER);
        startServerItem.setEnabled(!servletServer.isStarted());
        startServerItem.addActionListener(this);
        menu.add(startServerItem);
        
        stopServerItem = new JMenuItem(STOP_SERVER);
        stopServerItem.setActionCommand(STOP_SERVER);
        stopServerItem.setEnabled(servletServer.isStarted());
        stopServerItem.addActionListener(this);
        menu.add(stopServerItem);
        
        menu.addSeparator();
        
        quitItem = new JMenuItem(QUIT);
        quitItem.setActionCommand(QUIT);
        quitItem.addActionListener(this);
        menu.add(quitItem);
        
        // Set up the actual systray icon.
        final ImageIcon icon = new ImageIcon("images/systray.png");
        final TrayIcon ti = new TrayIcon(icon, "Control Bookmarker", menu);

        // Action listener for left click.
        ti.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	//menu.setVisible(true);
            }
        });
               
        final SystemTray tray = SystemTray.getDefaultSystemTray();
        tray.addTrayIcon(ti);
	}

	protected void toggleServerMenu()
	{
		startServerItem.setEnabled(!startServerItem.isEnabled());
		stopServerItem.setEnabled(!stopServerItem.isEnabled());
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent ae)
	{
		try
		{
			if (ae.getActionCommand().equals(START_SERVER))
			{
				servletServer.start();
				toggleServerMenu();
			}
			else if (ae.getActionCommand().equals(STOP_SERVER))
			{
				servletServer.stop();
				toggleServerMenu();
			}
			else
			{
				servletServer.stop();
				System.exit(0);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}
}
