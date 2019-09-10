package com.sangupta.htmlgen.tags.body.text;

import com.sangupta.htmlgen.core.HtmlBodyElement;

public class Label extends HtmlBodyElement<Label> {
    public Label() {
        super("label", Label.class);
    }

    public Label(String text) {
        this();
        text(text);
    }
}
