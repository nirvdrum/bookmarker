/*
 * Created on Sep 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import java.util.*;

import org.apache.tapestry.*;
import org.apache.tapestry.callback.ICallback;
import org.objectstyle.cayenne.access.DataContext;
import org.objectstyle.cayenne.query.SelectQuery;

import net.negativetwenty.bookmarker.models.*;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Login extends ApplicationPage
{
    public abstract String getUsername();
    public abstract String getPassword();
    
    protected ICallback callback = null;
    
    public void login(final IRequestCycle cycle)
    {
        final Visit v = (Visit) getVisit();
        final DataContext dc = getDataContext();
        
        // Retrieve the user with the specified credentials.
        User user = User.login(dc, getUsername(), getPassword());
        
        // This is pretty ghetto, but I haven't thought much of a better way to do this yet (probably an ant task will do).
        // The basic idea is that there aren't any user accounts, then register the default one.
        if (user == null && getUsername().equals("admin") && getPassword().equals("password"))
        {
             // Get the list of all users.
    			SelectQuery query = new SelectQuery(User.class);
    			List users = dc.performQuery(query);
    			
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
    
    public void setCallback(final ICallback callback)
    {
        this.callback = callback;
    }
}
