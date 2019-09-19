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

package com.sangupta.htmlgen.tags.body.grouping;

import com.sangupta.htmlgen.core.HtmlAttribute;
import com.sangupta.htmlgen.core.HtmlBodyElement;

/**
 * 
 * @author sangupta
 *
 */
public class Div extends HtmlBodyElement<Div> {

	public Div() {
		super("div", Div.class);
	}

	//引用layui的一些标签所要用到的方法
	public Div filter(String filter) {
		addAttribute(new HtmlAttribute("lay-filter", filter));
		return this;
	}

	//引用layui的一些标签所要用到的方法
	public Div layid(String layid) {
		addAttribute(new HtmlAttribute("lay-id", layid));
		return this;
	}

	public Div event(String event) {
		addAttribute(new HtmlAttribute("lay-event", event));
		return this;
	}

	public Div title(String title) {
		addAttribute(new HtmlAttribute("title", title));
		return this;
	}


}
