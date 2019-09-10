package com.sangupta.htmlgen.tags.body.sections;

import com.sangupta.htmlgen.core.HtmlBodyElement;

/**
 * Created by hy on 2017/11/16.
 */
public class Select extends HtmlBodyElement<Select> {

    public Select() {
        super("Select", Select.class);
    }


    public Option option() {
        Option option = new Option();
        this.add(option);
        return option;
    }

    public Option option(String label, String value) {
        Option option = new Option();
        option.value(value);
        if (label != null) {
            option.text(label);
        }
        this.add(option);
        return option;
    }
}