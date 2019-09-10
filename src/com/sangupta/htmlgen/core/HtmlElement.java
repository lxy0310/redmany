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

import com.sangupta.htmlgen.HtmlBody;
import com.sangupta.htmlgen.tags.Text;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Link;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sangupta
 */
public class HtmlElement<T> implements HtmlNode {

    /**
     * The {@link Set} of {@link HtmlAttribute}s that this element contains
     */
    protected final Set<HtmlAttribute> attributes = new HashSet<HtmlAttribute>();

    /**
     * The {@link Set} of child {@link HtmlElement}s that htis element contains
     */
    protected final List<HtmlElement<?>> children = new ArrayList<HtmlElement<?>>();

    /**
     * The parent {@link HtmlElement} for this element
     */
    protected HtmlElement<?> parent;

    /**
     * The name of this element - the tag name
     */
    protected final String name;

    /**
     * The class for this element - used to cast back using generics
     */
    protected final Class<T> clazzOfT;

    /**
     * Whether this element supports children or not
     */
    protected boolean supportsChildren = true;

    /**
     * Indicates if the end of tag should be written back or not.
     */
    protected boolean outputEndOfTag = true;

    /**
     * Create an instance of a tag with given name, and class
     *
     * @param name     the name of the tag used when writing HTML
     * @param clazzOfT the {@link Class} of this tag, used to cast back in return
     *                 values
     */
    public HtmlElement(String name, Class<T> clazzOfT) {
        this.name = name.toLowerCase();
        this.clazzOfT = clazzOfT;
    }

    /**
     * Return the parent element of this element
     */
    public HtmlElement<?> parent() {
        return this.parent;
    }

    /**
     * Return the current instance in its own type
     *
     * @return the element on which this method was invoked
     */
    public T me() {
        return this.clazzOfT.cast(this);
    }

    /**
     * Find the HTML element matching the given {@link Class} in the current
     * elements ancestor hierarchy.
     *
     * @param clazz
     * @return
     */
    public <X extends HtmlElement<?>> X parent(Class<X> clazz) {
        HtmlElement<?> parent = this.parent;
        do {
            if (parent == null) {
                return null;
            }

            if (parent.getClass().equals(clazz)) {
                return clazz.cast(parent);
            }

            parent = parent.parent();
        } while (true);
    }

    /**
     * Return the nearest <code>BODY</code> element in the ancestor hierarchy.
     *
     * @return the parent {@link HtmlBody} element
     */
    public HtmlBody parentBody() {
        return parent(HtmlBody.class);
    }

    /**
     * Return the nearest <code>DIV</code> element in the ancestor hierarchy.
     *
     * @return the parent {@link Div} element
     */
    public Div parentDiv() {
        return parent(Div.class);
    }

    /**
     * Return the nearest <code>SPAN</code> element in the ancestor hierarchy.
     *
     * @return the parent {@link Span} element
     */
    public Span parentSpan() {
        return parent(Span.class);
    }

    /**
     * Set the parent ownership of this element
     *
     * @param parent the element to set as parent
     * @return the element on which this method was invoked
     */
    public T parent(HtmlElement<?> parent) {
        this.parent = parent;
        return clazzOfT.cast(this);
    }

    /**
     * Add the given attribute to the element.
     *
     * @param attribute the {@link HtmlAttribute} to add
     */
    public void addAttribute(HtmlAttribute attribute) {
        this.attributes.add(attribute);
    }

    /**
     * Add an attribute by the given name and value to the element. If the
     * attribute already exists, its value will be replaced with the given one.
     *
     * @param name  the attribute name
     * @param value the value of the attribute
     * @return the element on which the method was called
     */
    public T attr(String name, String value) {
        boolean added = this.attributes.add(new HtmlAttribute(name, value));
        if (!added) {
            // replace this value
            for (HtmlAttribute attr : this.attributes) {
                if (attr.name.equalsIgnoreCase(name)) {
                    attr.value = value;
                    break;
                }
            }
        }

        return clazzOfT.cast(this);
    }

    public HtmlAttribute getAttribute(String name) {
        for (HtmlAttribute attr : this.attributes) {
            if (attr.name.equalsIgnoreCase(name)) {
                return attr;
            }
        }
        return null;
    }

    /**
     * Add a new {@link Text} element to this element with the given content
     *
     * @param text the content to use
     * @return the element on which this method was invoked
     */
    public T text(String text) {
        this.addChild(new Text(text));
        return clazzOfT.cast(this);
    }

    /**
     * Add the given {@link HtmlElement} child to this element. If the current
     * element does not support child elements, the call will do nothing.
     * <p>
     * The parent of the incoming child will be set to this element
     *
     * @param child the child element to add
     * @return the element on which this method was invoked
     */
    protected T addChild(HtmlElement<?> child) {
        if (!this.supportsChildren) {
            return clazzOfT.cast(this);
        }

        child.parent(this);
        this.children.add(child);
        return clazzOfT.cast(this);
    }

    public T add(HtmlElement<?> child) {
        if (child == null) return this.clazzOfT.cast(this);
        return addChild(child);
    }

    public T css(String herf) {
        return addChild(new Link().href(herf).rel("stylesheet"));
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj instanceof String) {
            return this.name.equalsIgnoreCase((String) obj);
        }

        if (!(obj instanceof HtmlElement)) {
            return false;
        }

        return this.name.equals(((HtmlElement<?>) obj).name);
    }

    /**
     * Remove all child elements, attributes, and text content from this
     * element.
     */
    public void clear() {
        this.attributes.clear();
        this.children.clear();
    }

    /**
     * Set the ID attribute for this element
     *
     * @param id the ID value to set
     */
    public T id(String id) {
        this.attr("id", id);
        return this.clazzOfT.cast(this);
    }

    public String getId() {
        HtmlAttribute attribute = getAttribute("id");
        if (attribute != null) {
            return attribute.value;
        }
        return null;
    }

    public String getText() {
        for (HtmlElement<?> child : this.children) {
            if (child instanceof Text) {
                Text ele = (Text) child;
                String text = ele.text();
                return text;
            }
        }
        return null;
    }

    public T idAndName(String name) {
        this.attr("name", name);
        this.attr("id", name);
        return this.clazzOfT.cast(this);
    }

    public T name(String name) {
        this.attr("name", name);
        return this.clazzOfT.cast(this);
    }

    /**
     * Build HTML string for this element and it's children
     *
     * @param builder
     * @param indentLevel
     */
    public void build(StringBuilder builder, int indentLevel) {
        indent(builder, indentLevel);
        builder.append("<");
        builder.append(this.name);

        // check for attributes
        if (!this.attributes.isEmpty()) {
            for (HtmlAttribute attribute : this.attributes) {
                if (attribute.value == null) {
                    builder.append(' ');
                    builder.append(attribute.name);
                    builder.append(' ');
                } else {
                    builder.append(' ');
                    builder.append(attribute.name);
                    builder.append("=\"");
                    builder.append(attribute.value);
                    builder.append("\"");
                }
            }
        }

        // any custom attributes
        outCustomAttributes(builder);
        // for children
        if (!this.outputEndOfTag) {
            builder.append(" />");
        } else {

            // close the starting tag
            builder.append('>');

            for (HtmlElement<?> child : this.children) {
                if (child instanceof Text) {
                    Text ele = (Text) child;
                    String text = ele.text();
                    if (text != null) {
                        indent(builder, indentLevel + 1);
                        builder.append(text);
                    }
                } else {
                    child.build(builder, indentLevel + 1);
                }
            }
            if (this.children.size() != 0) {
                // close up
                indent(builder, indentLevel);
            }
            builder.append("</");
            builder.append(this.name);
            builder.append('>');
        }
    }

    /**
     * Output any custom attribute at the tag level if the child tags want to
     *
     * @param builder the {@link StringBuilder} element to use
     */
    protected void outCustomAttributes(StringBuilder builder) {

    }

    /**
     * Indent the builder at the given level. An indentation level causes 2
     * spaces to be added per level. A new-line character is added before
     * indentation is done.
     *
     * @param builder the {@link StringBuilder} instance in which indentation will
     *                be added
     * @param level   the indentation level
     */
    protected void indent(StringBuilder builder, int level) {
        builder.append('\n');
        for (int i = 0; i < level; i++) {
            builder.append("  ");
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        build(stringBuilder, 0);
        return stringBuilder.toString();
    }

    public void clearChildrens() {
        this.children.clear();
    }

    public void clearAllText() {
        if (!this.children.isEmpty()) {
            int count = this.children.size();
            for (int i = count - 1; i >= 0; i--) {
                HtmlElement<?> child = children.get(i);
                if (child instanceof Text) {
                    children.remove(i);
                }
            }
        }
    }
}
