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
    public static User login(DataContext context, String username, String password) 
    {
        Map parameters = new HashMap();
        parameters.put("username", username);
        parameters.put("password", password);

        List objects = context.performQuery("UserLogin", parameters, true);
        
        if (objects.size() > 0)
        {
            return (User) objects.get(0);
        }

        return null;
    }
}
