/*
 * Created on May 23, 2005
 *
 * TODO Insert license or copyright statement.
 */

package net.negativetwenty.bookmarker.trees;


import java.io.Serializable;

import org.objectstyle.cayenne.DataObjectUtils;

import net.negativetwenty.bookmarker.models.Category;
import net.sf.tacos.model.IKeyProvider;

public class TreeKeyProvider implements IKeyProvider
{
    public Serializable getKey(final Object o)
    {
        Category c = (Category) o;
        
        return DataObjectUtils.intPKForObject(c);
    }
}
