package com.sangupta.htmlgen.tags.body.forms;

import com.sangupta.htmlgen.core.HtmlBodyElement;

public class Button extends HtmlBodyElement<Button> {
    public Button() {
        super("button", Button.class);
    }

    public Button(String text) {
        this();
        text(text);
    }
}
