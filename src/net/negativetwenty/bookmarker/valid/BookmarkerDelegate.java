/*
 * Created on Jan 29, 2005
 *
 * TODO Insert license or copyright statement.
 */

package net.negativetwenty.bookmarker.valid;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.valid.IValidator;
import org.apache.tapestry.valid.ValidationDelegate;

/**
 * TODO Briefly describe the class or interface. 
 *
 * @author nirvdrum
 */
public class BookmarkerDelegate extends ValidationDelegate
{
    public void writeLabelPrefix(final IFormComponent component, final IMarkupWriter writer, final IRequestCycle cycle)
    {
        if (isInError(component))
        {
            writer.begin("span");
            writer.attribute("class", "label-error");
        }
        else
        {
            writer.begin("span");
            writer.attribute("class", "label");
        }
    }
    
    public void writeLabelSuffix(final IFormComponent component, final IMarkupWriter writer, final IRequestCycle cycle)
    {
        writer.end();
    }
    
    public void writePrefix(final IMarkupWriter writer, final IRequestCycle cycle, 
            final IFormComponent component, final IValidator validator)
    {
      //  writer.begin("div");
    }
    
    public void writeSuffix(final IMarkupWriter writer, final IRequestCycle cycle, 
            final IFormComponent component, final IValidator validator)
    {
        if (null != validator && validator.isRequired())
        {
            writer.printRaw("&nbsp;");
            writer.begin("span");
            writer.attribute("class", "required-marker");
            writer.print("*");
            writer.end();
        }
        
        //writer.end();
    }
}
