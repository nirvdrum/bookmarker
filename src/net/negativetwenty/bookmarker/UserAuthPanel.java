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
    public abstract void setErrorMessage(String errorMessage);
    
    public void submit(final IRequestCycle cycle)
    {
        final boolean verifypw = getVerifypw();
        
        if (verifypw == true)
        {
            String password = getPassword();
            String verifyPassword = getVerifyPassword();
            
            // TODO We want to show some sort of error message no doubt.
            if (password.equals(verifyPassword) == false)
            {
                setErrorMessage("The passwords do not match up.  Please try again.");
                return;
            }
        }
        
        getListener().actionTriggered(this, cycle);
    }
}
