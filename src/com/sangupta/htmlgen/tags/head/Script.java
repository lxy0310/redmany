/**
 *
 * html-gen - HTML generation library
 * Copyright (c) 2014-2015, Sandeep Gupta
 * 
 * http://sangupta.com/projects/htmlgen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.htmlgen.tags.head;

import com.sangupta.htmlgen.core.HtmlElement;

/**
 * 
 * @author sangupta
 *
 */
public class Script extends HtmlElement<Script> {

	public Script() {
		super("script", Script.class);
        attr("type","text/javascript");
	}
	
	public Script(String src) {
		this();
		this.src(src);
	}
	
	public Script src(String src) {
		this.attr("src", src);
		return this;
	}
	
}