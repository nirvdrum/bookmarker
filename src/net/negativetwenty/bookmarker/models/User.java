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

package net.negativetwenty.bookmarker.models;

import net.negativetwenty.bookmarker.models.auto._User;

import java.util.*;
import org.objectstyle.cayenne.access.*;

public class User extends _User 
{
    /**
     * Attempts to find User for given userName and password. Returns such object
     * if found or null if not.
     */
    public static User login(final DataContext context, final String username, final String password) 
    {
        final Map parameters = new HashMap();
        parameters.put("username", username);
        parameters.put("password", password);

        final List objects = context.performQuery("UserLogin", parameters, true);
        
        if (objects.size() > 0)
        {
            return (User) objects.get(0);
        }

        return null;
    }
}
