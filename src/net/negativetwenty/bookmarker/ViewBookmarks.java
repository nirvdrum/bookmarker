/*
 * Created on Jul 24, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import net.negativetwenty.bookmarker.models.*;

import org.apache.tapestry.*;
import org.apache.tapestry.event.*;
import org.objectstyle.cayenne.*;
import org.objectstyle.cayenne.access.*;


/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ViewBookmarks extends ApplicationPage
{	
    public void linkClicked(IRequestCycle cycle)
    {
        ObjectId id = (ObjectId) cycle.getServiceParameters()[0];
        Bookmark b = (Bookmark) DataObjectUtils.objectForPK(getDataContext(), id);
        b.addClick();

        throw new RedirectException(b.getUrl());
    }
}
