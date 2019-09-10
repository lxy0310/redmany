package com.sangupta.htmlgen.tags.body.sections;

import com.sangupta.htmlgen.core.HtmlBodyElement;

/**
 * Created by Administrator on 2018/4/4 0004.
 */
public class Video extends HtmlBodyElement<Video> {


    public Video() {
        super("video", Video.class);
    }

    public Video(String src) {
        this();
        this.attr("src", src);
    }
    public Video src(String url) {
        this.attr("src", url);
        return this;
    }
}
