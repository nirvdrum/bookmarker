package net.negativetwenty.bookmarker.models;

import net.negativetwenty.bookmarker.models.auto._Bookmark;

public class Bookmark extends _Bookmark 
{
    public void addClick()
    {
        setClickCount(new Integer(getClickCount().intValue() + 1));
        getDataContext().commitChanges();
    }
    
    public void resetClicks()
    {
        setClickCount(new Integer(0));
        getDataContext().commitChanges();
    }
}



