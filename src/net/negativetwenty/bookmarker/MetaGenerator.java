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

/*
 * Created on Sep 30, 2004
 */
package net.negativetwenty.bookmarker;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRender;
import org.apache.tapestry.IRequestCycle;

/**
 * Inserts the "Live Bookmarks" meta-tag into the <HEAD> tags.
 * 
 * @author nirvdrum
 */
public class MetaGenerator implements IRender
{
    /**
     * @see org.apache.tapestry.IRender#render(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
     */
    public void render(final IMarkupWriter writer, final IRequestCycle cycle)
    {
        writer.beginEmpty("link");
        writer.attribute("rel", "alternate");
        writer.attribute("title", "Bookmarker RSS");
        writer.attribute("href", "bookmarks.rdf");
        writer.attribute("type", "application/rss+xml");   
    }

}
