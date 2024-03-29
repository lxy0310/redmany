/**
 * html-gen - HTML generation library
 * Copyright (c) 2014-2015, Sandeep Gupta
 * <p>
 * http://sangupta.com/projects/htmlgen
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sangupta.htmlgen.core;

import com.sangupta.htmlgen.tags.Pre;
import com.sangupta.htmlgen.tags.body.edits.DeletedText;
import com.sangupta.htmlgen.tags.body.edits.InsertedText;
import com.sangupta.htmlgen.tags.body.embed.Details;
import com.sangupta.htmlgen.tags.body.embed.IFrame;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.embed.Map;
import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Form;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.*;
import com.sangupta.htmlgen.tags.body.sections.*;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.text.*;
import com.sangupta.htmlgen.tags.head.Script;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author sangupta
 */
public class HtmlBodyElement<T> extends HtmlElement<T> {

    /**
     * The list of CSS classes associated with this element
     */
    protected final Set<String> cssClasses = new LinkedHashSet<String>();

    /**
     * The list of HTML style attributes associated with this element
     */
    protected final HashMap<String, HtmlStyle> styles = new HashMap<>();

    /**
     * Construct a new instance for the given element name
     *
     * @param name
     * @param clazzOfT
     */
    public HtmlBodyElement(String name, Class<T> clazzOfT) {
        super(name, clazzOfT);
    }

    /**
     * Remove all child elements, attributes, css, styles and text content from this element.
     */
    public void clear() {
        super.clear();
        this.cssClasses.clear();
        this.styles.clear();
    }

    /**
     * Clear all existing CSS classes and add the given one as the
     * only css class to this element.
     *
     * @param cssClass
     * @return
     */
    public T cssClass(String cssClass) {
        this.cssClasses.clear();
        this.cssClasses.add(cssClass);
        return clazzOfT.cast(this);
    }


    public Label label(String text) {
        Label label = new Label(text);
        add(label);
        return label;
    }

    public Label label() {
        Label label = new Label();
        add(label);
        return label;
    }

    public T script(String text) {
        Script script = new Script();
        script.text(text);
        return addChild(script);
    }

    public T js(String src) {
        Script script = new Script();
        script.src(src);
        return addChild(script);
    }

    public T hidden() {
        attr("hidden", null);
        return clazzOfT.cast(this);
    }

    /**
     * Add a CSS class to this element.
     *
     * @param cssClass
     * @return
     */
    public T addCssClass(String cssClass) {
        this.cssClasses.add(cssClass);
        return clazzOfT.cast(this);
    }

    /**
     * Remove a CSS class from this element.
     *
     * @param cssClass
     * @return
     */
    public T removeCssClass(String cssClass) {
        this.cssClasses.remove(cssClass);
        return clazzOfT.cast(this);
    }

    public T onError(String func) {
        this.attr("onerror", func);
        return clazzOfT.cast(this);
    }

    public T onClick(String func) {
        this.attr("onClick", func);
        return clazzOfT.cast(this);
    }

    public T onChange(String func) {
        this.attr("onChange", func);
        return clazzOfT.cast(this);
    }

    /**
     * Add a new style param to this element
     *
     * @param name
     * @param value
     * @return
     */
    public T style(String name, String value) {
        return this.style(new HtmlStyle(name, value));
    }

    /**
     * Add a new style param to this element
     */
    public T styles(String var) {
        if(var!=null){
        String[] vs = var.split(";");
        for (String e : vs) {
            String[] ws = e.split(":");
            if (ws.length > 1) {
                this.style(ws[0].trim(), ws[1].trim());
            } else {
                System.err.println("styles:" + var);
            }
        }
        }
        return (T) this;
    }

    /**
     * Add a new style param to this element that has an integer value.
     *
     * @param name
     * @param value
     * @return
     */
    public T style(String name, int value) {
        return this.style(new HtmlStyle(name, String.valueOf(value)));
    }

    public T style(HtmlStyle htmlStyle) {
        addStyle(htmlStyle);
        return this.clazzOfT.cast(this);
    }

    private void addStyle(HtmlStyle htmlStyle) {
        this.styles.put(htmlStyle.name, htmlStyle);
    }

    /**
     * Add an empty <code>DIV</code> to the element
     *
     * @return
     */
    public Div div() {
        return this.div(new Div());
    }

    public Button button(String text) {
        Button button = new Button(text);
        this.add(button);
        return button;
    }

    public Button button() {
        Button button = new Button();
        this.add(button);
        return button;
    }

    /**
     * Add given DIV to this element
     *
     * @param div
     * @return
     */
    public Div div(Div div) {
        this.addChild(div);
        return div;
    }

    /**
     * Add an <code>IFRAME</code> with the given URL to the element
     *
     * @param src
     * @return
     */
    public IFrame iframe(String src) {
        return this.iframe(new IFrame(src));
    }

    /**
     * Add the given <code>IFRAME</code> to the element
     *
     * @param iframe
     * @return
     */
    public IFrame iframe(IFrame iframe) {
        this.addChild(iframe);
        return iframe;
    }

    /**
     * Add a new <code>H1</code> heading to the element with
     * given text.
     *
     * @param text
     * @return
     */
    public T h1(String text) {
        return this.h1(new H1(text));
    }

    /**
     * Add a new <code>H1</code> heading to the element with
     * given text and CSS class
     *
     * @param text
     * @param cssClass
     * @return
     */
    public T h1(String text, String cssClass) {
        H1 h1 = new H1(text);
        h1.addCssClass(cssClass);

        return this.h1(h1);
    }

    /**
     * Add the given <code>H1</code> heading to the element.
     *
     * @param h1
     * @return
     */
    public T h1(H1 h1) {
        return this.addChild(h1);
    }

    /**
     * Add a new <code>H2</code> heading to the element with
     * given text.
     *
     * @param text
     * @return
     */
    public T h2(String text) {
        return this.h2(new H2(text));
    }

    /**
     * Add a new <code>H2</code> heading to the element with
     * given text and CSS class
     *
     * @param text
     * @param cssClass
     * @return
     */
    public T h2(String text, String cssClass) {
        H2 h2 = new H2(text);
        h2.addCssClass(cssClass);

        return this.h2(h2);
    }

    /**
     * Add the given <code>H2</code> heading to the element.
     *
     * @param h2
     * @return
     */
    public T h2(H2 h2) {
        return this.addChild(h2);
    }

    /**
     * Add a new <code>H3</code> heading to the element with
     * given text.
     *
     * @param text
     * @return
     */
    public T h3(String text) {
        return this.h3(new H3(text));
    }

    /**
     * Add a new <code>H3</code> heading to the element with
     * given text and CSS class
     *
     * @param text
     * @param cssClass
     * @return
     */
    public T h3(String text, String cssClass) {
        H3 h3 = new H3(text);
        h3.addCssClass(cssClass);

        return this.h3(h3);
    }

    /**
     * Add the given <code>H3</code> heading to the element.
     *
     * @param h3
     * @return
     */
    public T h3(H3 h3) {
        return this.addChild(h3);
    }

    /**
     * Add a new <code>H4</code> heading to the element with
     * given text.
     *
     * @param text
     * @return
     */
    public T h4(String text) {
        return this.h4(new H4(text));
    }

    /**
     * Add a new <code>H4</code> heading to the element with
     * given text and CSS class
     *
     * @param text
     * @param cssClass
     * @return
     */
    public T h4(String text, String cssClass) {
        H4 h4 = new H4(text);
        h4.addCssClass(cssClass);

        return this.h4(h4);
    }

    /**
     * Add the given <code>H4</code> heading to the element.
     *
     * @param h4
     * @return
     */
    public T h4(H4 h4) {
        return this.addChild(h4);
    }

    /**
     * Add a new <code>H5</code> heading to the element with
     * given text.
     *
     * @param text
     * @return
     */
    public T h5(String text) {
        return this.h5(new H5(text));
    }

    /**
     * Add a new <code>H5</code> heading to the element with
     * given text and CSS class
     *
     * @param text
     * @param cssClass
     * @return
     */
    public T h5(String text, String cssClass) {
        H5 h5 = new H5(text);
        h5.addCssClass(cssClass);

        return this.h5(h5);
    }

    /**
     * Add the given <code>H5</code> heading to the element.
     *
     * @param h5
     * @return
     */
    public T h5(H5 h5) {
        return this.addChild(h5);
    }

    /**
     * Add a new <code>H6</code> heading to the element with
     * given text.
     *
     * @param text
     * @return
     */
    public T h6(String text) {
        return this.h6(new H6(text));
    }

    /**
     * Add a new <code>H6</code> heading to the element with
     * given text and CSS class
     *
     * @param text
     * @param cssClass
     * @return
     */
    public T h6(String text, String cssClass) {
        H6 h6 = new H6(text);
        h6.addCssClass(cssClass);

        return this.h6(h6);
    }

    /**
     * Add the given <code>H6</code> heading to the element.
     *
     * @param h6
     * @return
     */
    public T h6(H6 h6) {
        return this.addChild(h6);
    }

    /**
     * Add an empty <code>SPAN</code> to the element
     *
     * @return
     */
    public Span span() {
        return this.span(new Span());
    }


    public T br() {
        return this.text("<br/>");
    }

    /**
     * Add a span with the given text to the element
     *
     * @param text
     * @return
     */
    public Span span(String text) {
        return this.span(new Span(text));

    }

    /**
     * Add given SPAN element to this element
     *
     * @param span
     * @return
     */
    public Span span(Span span) {
        this.addChild(span);
        return span;
    }

    /**
     * Add the img with given URL to the element
     *
     * @param src
     * @return
     */
    public Img img(String src) {
        return this.img(new Img(src));
    }

    public Video video(String src) {
        return this.video(new Video(src));
    }

    public Video video(Video video) {
        this.addChild(video);
        return video;
    }

    public P p() {
        P p = new P();
        this.addChild(p);
        return p;
    }

    public P p(String text) {
        P p = new P(text);
        this.addChild(p);
        return p;
    }

    /**
     * Add the img with given URL and dimensions to the element
     *
     * @param src
     * @param width
     * @param height
     * @return
     */
    public Img img(String src, int width, int height) {
        Img image = new Img(src);
        image.width(width);
        image.height(height);
        return this.img(image);
    }

    /**
     * Add the given img to the element
     *
     * @param image
     * @return
     */
    public Img img(Img image) {
        this.addChild(image);
        return image;
    }

    public Input input() {
        Input input = new Input();
        this.addChild(input);
        return input;
    }

    public Input input(String type, String value) {
        return input(type, null, value);
    }
//    public Input input(String placeholder){return input(placeholder);}

    public Input input(String type, String name, String value) {
        Input input = new Input();
        input.type(type);
        input.name(name);
        input.value(value);
        this.addChild(input);
        return input;
    }


    public TextArea textArea() {
        TextArea textArea = new TextArea();
        addChild(textArea);
        return textArea;
    }

    public TextArea textArea(int rows) {
        return textArea(rows, 0);
    }

    public TextArea textArea(int rows, int cols) {
        TextArea textArea = new TextArea();
        if (rows > 0) {
            textArea.rows(rows);
        }
        if (cols > 0) {
            textArea.cols(cols);
        }
        addChild(textArea);
        return textArea;
    }

    /**
     * Add an anchor for the given text.
     *
     * @param text
     * @return
     */
    public T anchor(String text) {
        return this.anchor(new Anchor(text));
    }

    /**
     * Add an anchor for the specified HREF and text
     *
     * @param text
     * @param href
     * @return
     */
    public T anchor(String text, String href) {
        return this.anchor(new Anchor(href, text));
    }

    /**
     * Add given anchor to this element
     *
     * @param anchor
     * @return
     */
    public T anchor(Anchor anchor) {
        return this.addChild(anchor);
    }

    public UL ul() {
        return this.ul(new UL());
    }

    public UL ul(String cssClass) {
        return this.ul(new UL(cssClass));
    }

    public UL ul(UL ul) {
        this.addChild(ul);
        return ul;
    }

    public OrderedList ol() {
        return this.ol(new OrderedList());
    }

    public OrderedList ol(String cssClass) {
        return this.ol(new OrderedList(cssClass));
    }

    public OrderedList ol(OrderedList ol) {
        this.addChild(ol);
        return ol;
    }

    public Form form() {
        return this.form(new Form());
    }

    public Form form(String action) {
        return this.form(new Form(action));
    }

    public Form form(String action, String method) {
        return this.form(new Form(action, method));
    }

    public Form form(Form form) {
        this.addChild(form);
        return form;
    }

    public Header header() {
        return this.header(new Header());
    }

    public Header header(String text) {
        return this.header(new Header(text));
    }

    public Header header(String text, String cssClass) {
        return this.header(new Header(text, cssClass));
    }

    public Header header(Header header) {
        this.addChild(header);
        return header;
    }

    public Address address(String text) {
        return this.address(new Address(text));
    }

    public Address address(Address address) {
        this.addChild(address);
        return address;
    }

    public A a() {
        A a = new A();
        return this.url(a);
    }

    public A a(String url) {
        A a = new A(url);
        return this.url(a);
    }

    public A a(String url, String title) {
        A a = new A(url);
        a.attr("title", title);
        return this.url(a);
    }

    public A url(A url) {
        this.addChild(url);
        return url;
    }


    public Select select() {
        Select select = new Select();
        this.add(select);
        return select;
    }


    public Article article() {
        return this.article(new Article());
    }

    public Article article(String cssClass) {
        return this.article(new Article(cssClass));
    }

    public Article article(Article article) {
        this.addChild(article);
        return article;
    }

    public Footer footer() {
        return this.footer(new Footer());
    }

    public Footer footer(String text) {
        return this.footer(new Footer(text));
    }

    public Footer footer(String text, String cssClass) {
        return this.footer(new Footer(text, cssClass));
    }

    public Footer footer(Footer footer) {
        this.addChild(footer);
        return footer;
    }

    public Nav nav() {
        return this.nav(new Nav());
    }

    public Nav nav(String cssClass) {
        return this.nav(new Nav(cssClass));
    }

    public Nav nav(Nav nav) {
        this.addChild(nav);
        return nav;
    }

    public Section section() {
        return this.section(new Section());
    }

    public Section section(String cssClass) {
        return this.section(new Section(cssClass));
    }

    public Section section(Section section) {
        this.addChild(section);
        return section;
    }

    public HGroup hgroup() {
        return this.hgroup(new HGroup());
    }

    public HGroup hgroup(String cssClass) {
        return this.hgroup(new HGroup(cssClass));
    }

    public HGroup hgroup(HGroup hgroup) {
        this.addChild(hgroup);
        return hgroup;
    }

    public T hr() {
        return this.hr(new HorizontalRule());
    }

    public T hr(String cssClass) {
        return this.hr(new HorizontalRule(cssClass));
    }

    public T hr(HorizontalRule hr) {
        return this.addChild(hr);
    }

    public Figure figure() {
        return this.figure(new Figure());
    }

    public Figure figure(String imageSrc) {
        return this.figure(new Figure(imageSrc));
    }

    public Figure figure(String imageSrc, String cssClass) {
        return this.figure(new Figure(imageSrc, cssClass));
    }

    public Figure figure(Img image) {
        return this.figure(new Figure(image));
    }

    public Figure figure(Img image, FigureCaption caption) {
        return this.figure(new Figure(image, caption));
    }

    public Figure figure(Figure figure) {
        this.addChild(figure);
        return figure;
    }

    public Main main() {
        return this.main(new Main());
    }

    public Main main(String cssClass) {
        return this.main(new Main(cssClass));
    }

    public Main main(Main main) {
        this.addChild(main);
        return main;
    }

    public BlockQuote blockQuote() {
        return this.blockQuote(new BlockQuote());
    }

    public BlockQuote blockQuote(String cssClass) {
        return this.blockQuote(new BlockQuote(cssClass));
    }

    public BlockQuote blockQuote(String cssClass, String cite) {
        return this.blockQuote(new BlockQuote(cssClass, cite));
    }

    public BlockQuote blockQuote(BlockQuote blockQuote) {
        this.addChild(blockQuote);
        return blockQuote;
    }

    public InlineQuote inlineQuote() {
        return this.inlineQuote(new InlineQuote());
    }

    public InlineQuote inlineQuote(String cssClass) {
        return this.inlineQuote(new InlineQuote(cssClass));
    }

    public InlineQuote inlineQuote(String cssClass, String cite) {
        return this.inlineQuote(new InlineQuote(cssClass, cite));
    }

    public InlineQuote inlineQuote(InlineQuote inlineQuote) {
        this.addChild(inlineQuote);
        return inlineQuote;
    }

    public Cite cite() {
        return this.cite(new Cite());
    }

    public Cite cite(String cssClass) {
        return this.cite(new Cite(cssClass));
    }

    public Cite cite(Cite cite) {
        this.addChild(cite);
        return cite;
    }

    public Details details() {
        return this.details(new Details());
    }

    public Details details(String cssClass) {
        return this.details(new Details(cssClass));
    }

    public Details details(Details details) {
        this.addChild(details);
        return details;
    }

    public Table table() {
        return this.table(new Table());
    }

    public Table table(String cssClass) {
        return this.table(new Table(cssClass));
    }

    public Table table(Table table) {
        this.addChild(table);
        return table;
    }

    public T em() {
        return this.em(new Emphasis());
    }

    public T em(String text) {
        return this.em(new Emphasis(text));
    }

    public T em(String text, String cssClass) {
        return this.em(new Emphasis(text, cssClass));
    }

    public T em(Emphasis em) {
        return this.addChild(em);
    }

    public T abbr() {
        return this.abbr(new Abbreviation());
    }

    public T abbr(String text) {
        return this.abbr(new Abbreviation(text));
    }

    public T abbr(String text, String cssClass) {
        return this.abbr(new Abbreviation(text, cssClass));
    }

    public T abbr(Abbreviation abbr) {
        return this.addChild(abbr);
    }

    public T bold() {
        return this.bold(new Bold());
    }

    public T bold(String text) {
        return this.bold(new Bold(text));
    }

    public T bold(String text, String cssClass) {
        return this.bold(new Bold(text, cssClass));
    }

    public T bold(Bold bold) {
        return this.addChild(bold);
    }

    public T code() {
        return this.code(new Code());
    }

    public T code(String text) {
        return this.code(new Code(text));
    }

    public T code(String text, String cssClass) {
        return this.code(new Code(text, cssClass));
    }

    public T code(Code code) {
        return this.addChild(code);
    }

    public T italic() {
        return this.italic(new Italic());
    }

    public T italic(String text) {
        return this.italic(new Italic(text));
    }

    public T italic(String text, String cssClass) {
        return this.italic(new Italic(text, cssClass));
    }

    public T italic(Italic italic) {
        return this.addChild(italic);
    }

    public T underline() {
        return this.underline(new Underline());
    }

    public T underline(String text) {
        return this.underline(new Underline(text));
    }

    public T underline(String text, String cssClass) {
        return this.underline(new Underline(text, cssClass));
    }

    public T underline(Underline underline) {
        return this.addChild(underline);
    }

    public T small() {
        return this.small(new Small());
    }

    public T small(String text) {
        return this.small(new Small(text));
    }

    public T small(String text, String cssClass) {
        return this.small(new Small(text, cssClass));
    }

    public T small(Small small) {
        return this.addChild(small);
    }

    public T strong() {
        return this.strong(new Strong());
    }

    public T strong(String text) {
        return this.strong(new Strong(text));
    }

    public T strong(String text, String cssClass) {
        return this.strong(new Strong(text, cssClass));
    }

    public T strong(Strong strong) {
        return this.addChild(strong);
    }

    public T sub() {
        return this.sub(new Subscript());
    }

    public T sub(String text) {
        return this.sub(new Subscript(text));
    }

    public T sub(String text, String cssClass) {
        return this.sub(new Subscript(text, cssClass));
    }

    public T sub(Subscript sub) {
        return this.addChild(sub);
    }

    public T sup() {
        return this.sup(new Superscript());
    }

    public T sup(String text) {
        return this.sup(new Superscript(text));
    }

    public T sup(String text, String cssClass) {
        return this.sup(new Superscript(text, cssClass));
    }

    public T sup(Superscript sup) {
        return this.addChild(sup);
    }

    public T ins() {
        return this.ins(new InsertedText());
    }

    public T ins(String text) {
        return this.ins(new InsertedText(text));
    }

    public T ins(String text, String cssClass) {
        return this.ins(new InsertedText(text, cssClass));
    }

    public T ins(InsertedText ins) {
        return this.addChild(ins);
    }

    public DeletedText del() {
        return this.del((String) null);
    }

    public DeletedText del(String text) {
        DeletedText del = new DeletedText(text);
        this.del(del);
        return del;
    }

    public DeletedText del(String text, String cssClass) {
        DeletedText del = new DeletedText(text, cssClass);
        this.del(del);
        return del;
    }

    public T del(DeletedText del) {
        return this.addChild(del);
    }

    public Map map() {
        return this.map(new Map());
    }

    public Map map(String name) {
        return this.map(new Map(name));
    }

    public Map map(Map map) {
        this.addChild(map);
        return map;
    }

    public Pre pre() {
        return this.pre(new Pre());
    }

    public Pre pre(String text) {
        return this.pre(new Pre(text));
    }

    public Pre pre(String text, String cssClass) {
        return this.pre(new Pre(text, cssClass));
    }

    public Pre pre(Pre pre) {
        this.addChild(pre);
        return pre;
    }

    /**
     * Output custom attributes, if any, for this element
     */
    @Override
    protected void outCustomAttributes(StringBuilder builder) {
        if (!this.cssClasses.isEmpty()) {
            builder.append(" class=\"");
            for (String cssClass : this.cssClasses) {
                builder.append(cssClass);
                builder.append(' ');
            }
            builder.append('"');
        }

        // now for styles
        if (!this.styles.isEmpty()) {
            builder.append(" style=\"");
            for (HashMap.Entry<String, HtmlStyle> e : this.styles.entrySet()) {
                HtmlStyle style = e.getValue();
                builder.append(style.name);
                builder.append(": ");
                builder.append(style.value);
                builder.append(";");
            }
            builder.append('"');
        }
    }
}

