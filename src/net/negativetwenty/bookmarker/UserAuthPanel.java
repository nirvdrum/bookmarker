/*
 * Created on Dec 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.*;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class UserAuthPanel extends BaseComponent
{
    public abstract IActionListener getListener();
    public abstract String getUsername();
    public abstract String getPassword();
    public abstract boolean getVerifypw();
    public abstract String getVerifyPassword();
    
    public void submit(final IRequestCycle cycle)
    {
        boolean verifypw = getVerifypw();
        String username = getUsername();
        String password = getPassword();
        String venrifyhePassword = getVerifyPassword();
        
        getListener().actionTriggered(this, cycle);
    }
}
