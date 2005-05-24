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
package net.negativetwenty.bookmarker;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import net.negativetwenty.bookmarker.models.*;

/**
 * Allows a user to change his authentication credentials.
 * 
 * @author nirvdrum
 */
public abstract class ChangeAuth extends SecureApplicationPage
{
    public abstract void setUsername(final String username);
    public abstract String getUsername();
    public abstract String getPassword();
    
    /**
     * Initializes the username property to that of the currently logged-in user.
     * 
     * @param event
     */
    public void pageBeginRender(final PageEvent event)
    {
        Visit v = (Visit) getVisit();
        
        setUsername(v.getUser().getUsername());
    }
    
    /**
     * ChangeAuth listener.  Commits the changed authentication information to the database.
     * 
     * @param cycle
     */
    public void changeAuth(final IRequestCycle cycle)
    {
        // Fetch the currently logged-in user object so this op becomes a modification rather than addition to the db.
        Visit v = (Visit) getVisit();
        User u = v.getUser();
        
        // Set the new user property values and commit them to the database.
        u.setUsername(getUsername());
        u.setPassword(getPassword());
        u.getDataContext().commitChanges();
        
        // TODO This should be a callback to the currently active page.
        cycle.activate("Home");
    }
}
