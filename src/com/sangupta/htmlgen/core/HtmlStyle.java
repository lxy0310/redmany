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

package com.sangupta.htmlgen.core;

/**
 *
 * @author sangupta
 *
 */
public class HtmlStyle {

    protected final String name;

    protected String value;

    public HtmlStyle(String name, String value) {
        this.name = name == null ? "" : name.toLowerCase();
        this.value = value;
    }

    @Override
    public String toString() {
        return " " + this.name + "='" + this.value + "'";
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HtmlStyle)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        return this.name.equals(((HtmlStyle) obj).name);
    }
}