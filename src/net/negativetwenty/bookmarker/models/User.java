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

/**
 * Represents a user.
 *
 * @author nirvdrum
 */
public class User extends _User 
{
    /**
     * Attempts to log a user into the system.
     * 
     * @param context The DataContext to use for the db query.
     * @param username The username to check.
     * @param password The password to check.
     * @return The User if a valid login, null otherwise.
     */
    public static User login(final DataContext context, final String username, final String password) 
    {
        // Set up the parameters for the db query.
        final Map parameters = new HashMap();
        parameters.put("username", username);
        parameters.put("password", password);

        // Query the db.
        final List objects = context.performQuery("UserLogin", parameters, true);
        
        // If there was a matching User, return it.
        if (objects.size() > 0)
        {
            return (User) objects.get(0);
        }

        // Otherwise, return null to indicate invalid credentials.
        return null;
    }
}
