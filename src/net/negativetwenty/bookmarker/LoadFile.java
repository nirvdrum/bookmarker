/*
 * Created on Dec 22, 2004
 *
 * TODO Insert license or copyright statement.
 */

package net.negativetwenty.bookmarker;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import net.negativetwenty.bookmarker.models.Bookmark;
import net.negativetwenty.bookmarker.models.User;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.request.IUploadFile;
import org.objectstyle.cayenne.CayenneException;
import org.objectstyle.cayenne.util.XMLDecoder;

/**
 * TODO Briefly describe the class or interface. 
 *
 * @author nirvdrum
 */
public abstract class LoadFile extends SecureApplicationPage
{
    public abstract IUploadFile getFile();
    public abstract void setBookmarks(List bookmarks);
    
    /**
     * Submit listener.  Handles the loading of an XML file for import.
     * 
     * @param cycle
     */
    public void submit(final IRequestCycle cycle)
    {
        final Visit v = (Visit) getVisit();
        final IUploadFile file = getFile();
        
        if (null == file)
        {
            return;
        }
        
        final Reader r = new InputStreamReader(file.getStream());
        final XMLDecoder decoder = new XMLDecoder();
        try
        {
            final List bookmarks = decoder.decodeCollection(r);
            final User user = v.getUser();
            
            for (final Iterator it = bookmarks.iterator(); it.hasNext();)
            {
                final Bookmark b = (Bookmark) it.next();
                user.getDataContext().registerNewObject(b);
                b.setCreatedBy(user);
            }
            
            setBookmarks(bookmarks);
        }
        catch (CayenneException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
