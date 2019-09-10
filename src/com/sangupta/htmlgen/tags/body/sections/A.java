package com.sangupta.htmlgen.tags.body.sections;

import com.sangupta.htmlgen.core.HtmlAttribute;
import com.sangupta.htmlgen.core.HtmlBodyElement;

public class A extends HtmlBodyElement<A> {

    public A() {
        super("a", A.class);
    }

    public A(String url) {
        this();
        herf(url);
    }

    public A herf(String url) {
        addAttribute(new HtmlAttribute("href", url));
        return this;
    }
}