/*
 * Created on Jan 29, 2005
 *
 * TODO Insert license or copyright statement.
 */

package net.negativetwenty.bookmarker.valid;

import java.net.*;

import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.BaseValidator;
import org.apache.tapestry.valid.ValidatorException;

/**
 * TODO Briefly describe the class or interface. 
 *
 * @author nirvdrum
 */
public class UrlValidator extends BaseValidator
{

    /* (non-Javadoc)
     * @see org.apache.tapestry.valid.IValidator#toObject(org.apache.tapestry.form.IFormComponent, java.lang.String)
     */
    public Object toObject(final IFormComponent field, final String input) throws ValidatorException
    {
        if (true == checkRequired(field, input))
        {
            return null;
        }
        
        try
        {
            final URL ret = new URL(input);
            
            return ret;
        }
        catch (final MalformedURLException e)
        {
            throw new ValidatorException(e.getLocalizedMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.apache.tapestry.valid.IValidator#toString(org.apache.tapestry.form.IFormComponent, java.lang.Object)
     */
    public String toString(final IFormComponent field, final Object value)
    {
        if (null == value)
        {
            return null;
        }
        
        return value.toString();
    }

}
