/*
 * Created on Sep 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.*;
import org.apache.tapestry.callback.*;
import org.apache.tapestry.event.*;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SecureApplicationPage extends ApplicationPage implements PageValidateListener
{
    public void pageValidate(PageEvent event)
    {
        Visit visit = (Visit) getVisit();
        
        if (visit.isLoggedIn())
        {
            return;
        }
        
        IRequestCycle cycle = getRequestCycle();
        Login login = (Login) cycle.getPage("Login");
        login.setCallback(new PageCallback(this));
        throw new PageRedirectException(login);
    }
}
