/*
 * Created on Oct 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.*;
import org.apache.tapestry.callback.PageCallback;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Border extends BaseComponent
{
    public void login(IRequestCycle cycle)
    {
        Login login = (Login) cycle.getPage("Login");
        login.setCallback(new PageCallback(getPage()));
        throw new PageRedirectException(login);
    }
    
    public void addBookmark(IRequestCycle cycle)
    {
        AddBookmark ab = (AddBookmark) cycle.getPage("AddBookmark");
        ab.setBookmark(null);
        cycle.activate(ab);
    }
}
