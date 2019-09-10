package com.sangupta.htmlgen;

import com.sangupta.htmlgen.tags.body.grouping.Div;

public class HtmlTest {

    public static void main(String[] args) {
        Div div = new Div();
        div.br();
        div.text("hello");
        div.span().img("ime");
        System.out.println(div.toString());
    }
}
