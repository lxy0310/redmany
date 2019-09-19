package com.sangupta.htmlgen.tags.body.forms;

import com.sangupta.htmlgen.core.HtmlAttribute;
import com.sangupta.htmlgen.core.HtmlBodyElement;

public class Input extends HtmlBodyElement<Input> {
    public Input() {
        super("input", Input.class);
        this.outputEndOfTag = false;
    }

    public Input type(String type) {
        attr("type", type);
        return this;
    }

    public Input value(String value) {
        attr("value", value);
        return this;
    }
    public Input size(String value) {
        attr("size", value);
        return this;
    }
    public Input placeholder(String value) {
        attr("placeholder", value);
        return this;
    }

    public Input blur(String func){
        attr("blur", func);
        return this;
    }

    public Input required() {
        return super.attr("required ", null);
    }

    //引用layui的一些标签所要用到的方法
    public Input filter(String filter) {
        addAttribute(new HtmlAttribute("lay-filter", filter));
        return this;
    }

    public Input skin(String skin) {
        addAttribute(new HtmlAttribute("lay-skin", skin));
        return this;
    }

    public Input title(String title) {
        addAttribute(new HtmlAttribute("title", title));
        return this;
    }


}
