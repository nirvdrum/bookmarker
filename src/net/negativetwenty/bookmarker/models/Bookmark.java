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

import net.negativetwenty.bookmarker.models.auto._Bookmark;

/**
 * Represents a bookmark. 
 *
 * @author nirvdrum
 */
public class Bookmark extends _Bookmark 
{
    protected boolean importEntry = true;
    
    /**
     * Increments a Bookmark's click count value by 1.
     */
    public void addClick()
    {
        setClickCount(new Integer(getClickCount().intValue() + 1));
        getDataContext().commitChanges();
    }
    
    /**
     * Resets a Bookmark's click count to 0.
     */
    public void resetClicks()
    {
        setClickCount(new Integer(0));
        getDataContext().commitChanges();
    }
    
    /**
     * @return Returns the importEntry.
     */
    public boolean isImportEntry()
    {
        return importEntry;
    }
    
    /**
     * @param importEntry The importEntry to set.
     */
    public void setImportEntry(boolean importEntry)
    {
        this.importEntry = importEntry;
    }
}