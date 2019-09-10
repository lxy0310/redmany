/**
 * html-gen - HTML generation library
 * Copyright (c) 2014-2015, Sandeep Gupta
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

package com.sangupta.htmlgen.tags.body.embed;

import com.sangupta.htmlgen.core.HtmlBodyElement;

/**
 *
 * @author sangupta
 *
 */
public class Img extends HtmlBodyElement<Img> {

    public Img() {
        super("img", Img.class);
        this.outputEndOfTag = false;
    }

    public Img(String src) {
        this();
        this.attr("src", src);
    }

    public Img width(String width) {
        this.attr("width", String.valueOf(width));
        return this.clazzOfT.cast(this);
    }

    public Img height(String height) {
        this.attr("height", String.valueOf(height));
        return this.clazzOfT.cast(this);
    }

    public Img width(int width) {
        this.attr("width", String.valueOf(width));
        return this.clazzOfT.cast(this);
    }

    public Img height(int height) {
        this.attr("height", String.valueOf(height));
        return this.clazzOfT.cast(this);
    }

    public Img src(String url) {
        this.attr("src", url);
        return this;
    }
}
