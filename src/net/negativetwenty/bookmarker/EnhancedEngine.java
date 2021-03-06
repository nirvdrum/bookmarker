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
 * Created on Sep 23, 2004
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.engine.*;
import org.apache.tapestry.request.RequestContext;

/**
 * Provides the enhanced Tapestry error page.
 * 
 * @author nirvdrum
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
