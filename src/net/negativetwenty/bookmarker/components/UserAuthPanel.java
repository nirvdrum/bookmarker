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
 * Created on Dec 2, 2004
 */
package net.negativetwenty.bookmarker.components;

import org.apache.tapestry.*;

/**
 * Main logic for the UserAuthPanel component, which represents a form for user authentication.
 * 
 * @author nirvdrum
 */
public abstract class UserAuthPanel extends BaseComponent
{
    public abstract IActionListener getListener();
    public abstract String getUsername();
    public abstract String getPassword();
    public abstract boolean getVerifypw();
    public abstract String getVerifyPassword();
    public abstract void setErrorMessage(String errorMessage);
    
    /**
     * Submit listener.  Processes the form contents when the "Submit" button is clicked.
     * 
     * @param cycle
     */
    public void submit(final IRequestCycle cycle)
    {
        final boolean verifypw = getVerifypw();
        
        // If we need to verify passwords . . .
        if (verifypw == true)
        {
            String password = getPassword();
            String verifyPassword = getVerifyPassword();
            
            // check that the "password" and "verify password" fields match . . .
            if (password.equals(verifyPassword) == false)
            {
                // if they don't, set an error message and redisplay the same page.
                setErrorMessage("The passwords do not match up.  Please try again.");
                return;
            }
        }
        
        // If everything is fine (either no need to verify passwords or passwords verified correctly), then
        // trigger the supplied "listener" parameter for this component.
        getListener().actionTriggered(this, cycle);
    }
}
