package com.sangupta.htmlgen.tags.body.sections;

import com.sangupta.htmlgen.core.HtmlBodyElement;

/**
 * Created by hy on 2017/11/16.
 */
public class Option extends HtmlBodyElement<Option> {
    public Option() {
        super("option", Option.class);
    }

    public Option value(String value) {
        this.attr("value", value);
        return this;
    }

    public Option selected(){
        this.attr("selected", null);
        return this;
    }
}
