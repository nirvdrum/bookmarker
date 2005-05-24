/*
 * Created on May 23, 2005
 *
 * TODO Insert license or copyright statement.
 */

package net.negativetwenty.bookmarker.trees;


import java.util.*;

import org.objectstyle.cayenne.query.Ordering;

import net.negativetwenty.bookmarker.models.Bookmark;
import net.negativetwenty.bookmarker.models.Category;
import net.sf.tacos.model.ITreeContentProvider;

public class TreeContentProvider implements ITreeContentProvider
{
    protected List categories = null;
    
    public Collection getChildren(final Object parent)
    {
        if (parent instanceof Category)
        {
            final Category c = (Category) parent;
            
            final Ordering o = new Ordering("name", true);
            final List ret = c.getChildren();
            o.orderList(ret);
            
            return ret;
        }
        
        return Collections.EMPTY_LIST;
    }

    public boolean hasChildren(final Object parent)
    {
        if (getChildren(parent).size() > 0)
        {
            return true;
        }
        
        return false;
    }

    public Object getParent(final Object child)
    {
        if (child instanceof Bookmark)
        {
            final Bookmark b = (Bookmark) child;
            
            return b.getCategory();
        }
        
        if (child instanceof Category)
        {
            final Category c = (Category) child;
            
            return c.getParent();
        }
        
        return null;
    }

    public Collection getElements()
    {
        if (null != categories)
        {
            return categories;
        }
        
        return new ArrayList();
    }

    public void setCategories(final List categories)
    {
        this.categories = categories;
    }
}
