package com.sangupta.htmlgen.tags.body.text;

import com.sangupta.htmlgen.core.HtmlBodyElement;

public class P extends HtmlBodyElement<P> {
    public P(String text) {
        super("p", P.class);
        text(text);
    }

    public P() {
        super("p", P.class);
    }
}
