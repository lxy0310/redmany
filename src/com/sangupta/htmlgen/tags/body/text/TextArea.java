package com.sangupta.htmlgen.tags.body.text;

import com.sangupta.htmlgen.core.HtmlBodyElement;

public class TextArea extends HtmlBodyElement<TextArea> {
    public TextArea() {
        super("textarea", TextArea.class);
    }

    public TextArea cols(int cols) {
        attr("cols", cols + "");
        return this;
    }

    public TextArea rows(int rows) {
        attr("rows", rows + "");
        return this;
    }
    public TextArea placeholder(String value) {
        attr("placeholder", value);
        return this;
    }
}
