/**
 *
 * html-gen - HTML generation library
 * Copyright (c) 2014, Sandeep Gupta
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
import com.sangupta.htmlgen.tags.Text;

/**
 * 
 * @author sangupta
 *
 */
public class ListItem extends HtmlBodyElement<ListItem> {

	public ListItem() {
		super("li", ListItem.class);
	}
	
	public ListItem(String text) {
		this();
		this.text(text);
	}
	
	public ListItem(String text, String cssClass) {
		this(text);
		this.addCssClass(cssClass);
	}
	
	public ListItem text(String text) {
		this.addChild(new Text(text));
		return this;
	}

	//引用layui的一些标签所要用到的方法
	public ListItem layid(String layid) {
		addAttribute(new HtmlAttribute("lay-id", layid));
		return this;
	}
	
}
