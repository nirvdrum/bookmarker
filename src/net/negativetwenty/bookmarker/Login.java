/*
 * Created on Sep 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.*;
import org.apache.tapestry.callback.ICallback;
import org.apache.tapestry.html.BasePage;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Login extends BasePage
{
    public abstract String getUsername();
    public abstract String getPassword();
    
    protected ICallback callback;
    
    public void login(final IRequestCycle cycle)
    {
        final Visit v = (Visit) getVisit();
        
        v.authenticate(getUsername(), getPassword());
        
        if (callback == null)
        {
            cycle.activate("Home");
        }
        else
        {
            callback.performCallback(cycle);
        }
    }
    
    public void setCallback(final ICallback callback)
    {
        this.callback = callback;
    }
}
