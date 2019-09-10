package com.sangupta.htmlgen.tags.head;

import com.sangupta.htmlgen.core.HtmlElement;

import java.util.ArrayList;
import java.util.List;

public class Style extends HtmlElement<Style> {
    private List<StyleItem> list = new ArrayList<>();

    public Style() {
        super("style", Style.class);
        this.attr("type", "text/css");
    }

    public Style(String text) {
        this();
        this.text(text);
    }

    public Style add(StyleItem item) {
        list.add(item);
        return this;
    }

    public Style add(String name, String value) {
        return add(new StyleItem(name, value));
    }

    @Override
    protected void outCustomAttributes(StringBuilder builder) {
        clearAllText();
        for (StyleItem item : list) {
            this.text(item.toString());
        }
    }
}