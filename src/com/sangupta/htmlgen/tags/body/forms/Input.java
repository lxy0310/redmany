package com.sangupta.htmlgen.tags.body.forms;

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
}
