/*
 * Created on Dec 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.objectstyle.cayenne.access.DataContext;
import net.negativetwenty.bookmarker.models.*;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ChangeAuth extends SecureApplicationPage
{
    public abstract void setUsername(final String username);
    public abstract String getUsername();
    public abstract String getPassword();
    
    public void pageBeginRender(final PageEvent event)
    {
        Visit v = (Visit) getVisit();
        
        setUsername(v.getUser().getUsername());
    }
    
    public void changeAuth(final IRequestCycle cycle)
    {
        Visit v = (Visit) getVisit();
        User u = v.getUser();
        
        u.setUsername(getUsername());
        u.setPassword(getPassword());
        u.getDataContext().commitChanges();
        
        cycle.activate("Home");
    }
}
