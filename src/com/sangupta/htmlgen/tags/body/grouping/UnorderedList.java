/**
 * html-gen - HTML generation library
 * Copyright (c) 2014, Sandeep Gupta
 * <p>
 * http://sangupta.com/projects/htmlgen
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sangupta.htmlgen.tags.body.grouping;

import com.sangupta.htmlgen.core.HtmlBodyElement;

/**
 *
 * @author sangupta
 *
 */
public class UnorderedList extends HtmlBodyElement<UnorderedList> {

    public UnorderedList() {
        super("ul", UnorderedList.class);
    }

    public UnorderedList(String cssClass) {
        this();
        this.addCssClass(cssClass);
    }

    public UnorderedList li(String text) {
        return this.li(new ListItem(text));
    }

    public UnorderedList li(String text, String cssClass) {
        return this.li(new ListItem(text, cssClass));
    }

    public UnorderedList li(ListItem li) {
        return this.addChild(li);
    }

    public <T> UnorderedList li(HtmlBodyElement<T> li) {
        return this.addChild(li);
    }
}
