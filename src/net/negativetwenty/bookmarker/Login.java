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
 */
package net.negativetwenty.bookmarker;

import java.util.*;

import org.apache.tapestry.*;
import org.apache.tapestry.callback.ICallback;
import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.query.SelectQuery;

import net.negativetwenty.bookmarker.models.*;

/**
 * Handles logging into the system.
 * 
 * @author nirvdrum
 */
public abstract class Login extends ApplicationPage
{
    public abstract String getUsername();
    public abstract String getPassword();
    
    protected ICallback callback = null;
    
    /**
     * Login listener.  Attempts to log the user in.  If a login is successful, the Visit object is updated
     * to note this.
     * 
     * @param cycle
     */
    public void login(final IRequestCycle cycle)
    {
        final Visit v = (Visit) getVisit();
        final DataContext dc = getDataContext();
        
        // Retrieve the user with the specified credentials.
        User user = User.login(dc, getUsername(), getPassword());
        
        // This is pretty ghetto, but I haven't thought much of a better way to do this yet (probably an ant task will do).
        // The basic idea is that there aren't any user accounts, then register the default one.
        if (user == null && getUsername() != null && getPassword() != null && getUsername().equals("admin") && getPassword().equals("password"))
        {
             // Get the list of all users.
             final SelectQuery query = new SelectQuery(User.class);
             final List users = dc.performQuery(query);
    			
    			// Check that the list is empty.
    			if (users.isEmpty())
    			{
    			    user = new User();
    			    dc.registerNewObject(user);
    			    
    			    user.setUsername(getUsername());
    			    user.setPassword(getPassword());
    			    user.setCreated(new Date());
    			    dc.commitChanges();
    			}
        }
        
        // Store the user in the visit object.
        v.setUser(user);
        
        // Now forward to the correct page.
        if (callback == null)
        {
            cycle.activate("Home");
        }
        else
        {
            callback.performCallback(cycle);
            callback = null;
        }
    }
    
    /**
     * Used to indicate what page to redirect to after the login() listener is done.
     * 
     * @param callback The page to redirect to after a login.
     */
    public void setCallback(final ICallback callback)
    {
        this.callback = callback;
    }
}
