/*
 * Created on Sep 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRender;
import org.apache.tapestry.IRequestCycle;

/**
 * @author nirvdrum
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MetaGenerator implements IRender
{

    /* (non-Javadoc)
     * @see org.apache.tapestry.IRender#render(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
     */
    public void render(IMarkupWriter writer, IRequestCycle cycle)
    {
        writer.beginEmpty("link");
        writer.attribute("rel", "alternate");
        writer.attribute("title", "Bookmarker RSS");
        writer.attribute("href", "bookmarks.rdf");
        writer.attribute("type", "application/rss+xml");   
    }

}
