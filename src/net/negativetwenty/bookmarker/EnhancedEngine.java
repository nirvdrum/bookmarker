/*
 * Created on Sep 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import java.util.*;
import org.apache.tapestry.*;
import org.apache.tapestry.engine.*;
import org.apache.tapestry.request.RequestContext;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnhancedEngine extends BaseEngine
{
    protected String getExceptionPageName() 
    {
       return "exception:Exception";
    }
    
    protected Object createGlobal(final RequestContext context)
    {
        return new Global(getPropertySource());
    }
}
