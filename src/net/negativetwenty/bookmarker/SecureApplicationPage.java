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
    public void pageValidate(final PageEvent event)
    {
        final Visit visit = (Visit) getVisit();
        
        if (visit.isLoggedIn())
        {
            return;
        }
        
        final IRequestCycle cycle = getRequestCycle();
        final Login login = (Login) cycle.getPage("Login");
        login.setCallback(new PageCallback(this));
        throw new PageRedirectException(login);
    }
}
