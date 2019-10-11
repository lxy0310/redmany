package service.impl;

import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import page.Page;
import service.PagingService;

public class PagingServiceImpl implements PagingService {

    @Override
    public void addPagingMenuBar(Div div, Page page) {

        Div pageDiv=div.div();
        pageDiv.id("pageDiv");
        pageDiv.attr("width","100%");
        pageDiv.attr("style","text-align:center;padding: 20px 0;");

        Span currentPage= pageDiv.span();
        currentPage.text(page.getPageIndex()+"");
        if(page.getPageIndex()!=1) {
            //首页
            A firstPage = pageDiv.a();
            firstPage.id("firstPage");
            firstPage.attr("href", "javascript:pageJump('" + page.getCopformName() + "','" + page.getShowType() + "',1)");
            firstPage.text("首页");
            firstPage.addCssClass("paging");
            //上一页
            A prePage = pageDiv.a();
            prePage.id("prePage=");
            prePage.attr("href", "javascript:pageJump('" + page.getCopformName() + "','" + page.getShowType() + "'," + (page.getPageIndex() - 1 < 1 ? 1 : page.getPageIndex() - 1) + ")");
            prePage.text("上一页");
            firstPage.addCssClass("paging");
        }
        if(page.getPageIndex()!=page.getPageCount()){


        //下一页
        A nextPage=pageDiv.a();
        nextPage.id("nextPage");
        nextPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+(page.getPageIndex()+1>page.getPageCount()?page.getPageCount():page.getPageIndex()+1)+")");
        nextPage.text("下一页");

        //尾页
        A lastPage=pageDiv.a();
        lastPage.id("lastPage");
        lastPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+page.getPageCount()+")");
        lastPage.text("尾页");

        }
        //跳转栏
        Label goLabel=pageDiv.label();
        goLabel.text("跳转到:");
        goLabel.attr("for","goText");
        Input goText=pageDiv.input();
        goText.id("goText");
        goText.type("text");
        goText.value(page.getPageIndex()+"");
        goText.attr("style","width:20px;height:18px");
        A goPage=pageDiv.a();
        goPage.id("goPage");
        goPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"','goText')");
        goPage.text("Go");
    }
}
