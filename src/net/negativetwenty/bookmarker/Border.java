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
    
    public void logout(IRequestCycle cycle)
    {
        Visit v = (Visit) getPage().getVisit();
        v.setUser(null);
        cycle.activate("Home");
    }
    
    public void addBookmark(IRequestCycle cycle)
    {
        AddBookmark ab = (AddBookmark) cycle.getPage("AddBookmark");
        ab.setBookmark(null);
        cycle.activate(ab);
    }
}
