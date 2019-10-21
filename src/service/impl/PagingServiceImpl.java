package service.impl;

import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.sections.Option;
import com.sangupta.htmlgen.tags.body.sections.Select;
import com.sangupta.htmlgen.tags.body.text.Label;

import com.sangupta.htmlgen.tags.body.text.Span;

import page.Page;
import service.PagingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PagingServiceImpl implements PagingService {
    private List<Integer> optionValueList= Arrays.asList(5,10,20,30,50);
    @Override
    public void addPagingMenuBar(Div div, Page page) {

        Div pageDiv=div.div();
        pageDiv.id("pageDiv");
        pageDiv.attr("width","100%");
        pageDiv.attr("style","text-align:center;padding: 20px 0;");
        pageDiv.addCssClass("layui-elem-field layui-field-title");
        Div pageContent=pageDiv.div();
        pageContent.id("pageContent");
        pageContent.addCssClass("layui-box layui-laypage layui-laypage-default");
        Input input = pageDiv.input("hidden",String.valueOf(page.getPageCount()));//总页数
        input.id("PageCount");
        Input input2 = pageDiv.input("hidden",String.valueOf(page.getPageSize()));//当前页数
        input2.id("PageSize");

        Span dataCountPage= pageContent.span();
        dataCountPage.id("dataCount");
        dataCountPage.text("共 "+page.getDataCount()+" 条");
        dataCountPage.addCssClass("layui-laypage-count");


        A prePage = pageContent.a();
        prePage.id("prePage");
        prePage.text("上一页");
        if(page.getPageIndex()!=1) {
            //首页
          /*  A firstPage = pageDiv.a();
            firstPage.id("firstPage");
            firstPage.attr("href", "javascript:pageJump('" + page.getCopformName() + "','" + page.getShowType() + "',1)");
            firstPage.text("首页");
            firstPage.addCssClass("paging");*/
            //上一页
            prePage.addCssClass("layui-laypage-prev");
            prePage.attr("href", "javascript:pageJump('" + page.getCopformName() + "','" + page.getShowType() + "'," + (page.getPageIndex() - 1 < 1 ? 1 : page.getPageIndex() - 1) + ")");

            //第一页
            A firstPage=pageContent.a();
            firstPage.id("firstPage");
            firstPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',1)");
            firstPage.text("1");
            firstPage.addCssClass("layui-laypage-first");

        }else{
            prePage.addCssClass("layui-laypage-prev layui-disabled");
            prePage.attr("href","javascript:;");

            Span currPage=pageContent.span();
            currPage.id("currPage");
            currPage.addCssClass("layui-laypage-curr");
            currPage.text("<em class=\"layui-laypage-em\"></em><em>"+page.getPageIndex()+"</em></span>");
        }

          //是否生成省略号
        if(page.getPageIndex()-1>3){

            Span sprSpan=pageContent.span();
            sprSpan.id("sprSpan");
            sprSpan.addCssClass("layui-laypage-spr");
            sprSpan.text("...");
        }else {

            //生成2-5
            for (int i=2;i<=5 ;i++){
                if(i!=page.getPageIndex() && i<page.getPageCount()){
                    A butPage=pageContent.a();
                    butPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+i+")");
                    butPage.text(i+"");

                }else if(i<page.getPageCount()){

                    Span currPage=pageContent.span();
                    currPage.id("currPage");
                    currPage.addCssClass("layui-laypage-curr");
                    currPage.text("<em class=\"layui-laypage-em\"></em><em>"+page.getPageIndex()+"</em></span>");
                }


            }
        }
       if(page.getPageIndex()-1>3 && page.getPageCount()-page.getPageIndex()>=3){

           for (int i=page.getPageIndex()-2;i<=page.getPageIndex()+2;i++){
               if(i<0){
                   continue;
               }
               if(i>page.getPageCount()){
                   continue;
               }
               if(i!=page.getPageIndex()){
                   A butPage=pageContent.a();
                   butPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+i+")");
                   butPage.text(i+"");

               }else{

                   Span currPage=pageContent.span();
                   currPage.id("currPage");
                   currPage.addCssClass("layui-laypage-curr");
                   currPage.text("<em class=\"layui-laypage-em\"></em><em>"+page.getPageIndex()+"</em></span>");
               }


           }


       }

        if(page.getPageCount()-page.getPageIndex()>3){
            Span sprSpan=pageContent.span();
            sprSpan.id("sprSpan");
            sprSpan.addCssClass("layui-laypage-spr");
            sprSpan.text("...");

        }else if(page.getPageCount()-page.getPageIndex()<3){

            for (int i=page.getPageCount()-4;i<page.getPageCount();i++){
                if(i<=1){
                    continue;
                }
                if(i!=page.getPageIndex()){
                    A butPage=pageContent.a();
                    butPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+i+")");
                    butPage.text(i+"");

                }else{

                    Span currPage=pageContent.span();
                    currPage.id("currPage");
                    currPage.addCssClass("layui-laypage-curr");
                    currPage.text("<em class=\"layui-laypage-em\"></em><em>"+page.getPageIndex()+"</em></span>");
                }


            }


        }


        //最后一页
        if(page.getPageIndex()<page.getPageCount() && page.getPageCount()!=1){

            A lastPage=pageContent.a();
            lastPage.id("lastPage");
            lastPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+page.getPageCount()+")");
            lastPage.text(page.getPageCount()+"");
            lastPage.addCssClass("layui-laypage-last");

        }else if(page.getPageCount()!=1){
            Span currPage=pageContent.span();
            currPage.id("currPage");
            currPage.addCssClass("layui-laypage-curr");
            currPage.text("<em class=\"layui-laypage-em\"></em><em>"+page.getPageCount()+"</em></span>");

        }

        A nextPage=pageContent.a();
        nextPage.id("nextPage");
        nextPage.text("下一页");
        if(page.getPageIndex()<page.getPageCount()){
        //下一页
            nextPage.addCssClass("layui-laypage-next");
            nextPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+(page.getPageIndex()+1>page.getPageCount()?page.getPageCount():page.getPageIndex()+1)+")");

        //尾页
  /*      A lastPage=pageDiv.a();
        lastPage.id("lastPage");
        lastPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+page.getPageCount()+")");
        lastPage.text("尾页");
*/
        }else{
            nextPage.addCssClass("layui-laypage-next layui-disabled");
            nextPage.attr("href","javascript:;");
        }

        //pgaeSize
        Span pageSizeSpan=pageContent.span();
        pageSizeSpan.addCssClass("layui-laypage-limits");
        Select pageSizeSelect=pageSizeSpan.select();
        pageSizeSelect.id("pageSizeSelect");
        pageSizeSelect.onChange("pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+page.getPageIndex()+")");
        for (Integer value:
        optionValueList) {
            if(value==page.getPageSize()){
                pageSizeSelect.option(value+" 条/页",value+"").selected();
            }else{

                pageSizeSelect.option(value+" 条/页",value+"");
            }

        }
       //刷新
         A refreshPage=pageContent.a();
        refreshPage.text("<i class=\"layui-icon layui-icon-refresh\"></i>");
        refreshPage.addCssClass("layui-laypage-refresh");
        refreshPage.attr("href","javascript:pageJump('"+page.getCopformName()+"','"+page.getShowType()+"',"+page.getPageIndex()+")");

        Span skipPage=pageContent.span();
        skipPage.addCssClass("layui-laypage-skip");
        skipPage.text("到第<input type=\"text\" min=\"1\" id=\"goText\" value=\""+page.getPageIndex()+"\" class=\"layui-input\">页<button type=\"button\" onclick=\"pageJump('"+page.getCopformName()+"','"+page.getShowType()+"','goText')\"  class=\"layui-laypage-btn\" >确定</button>");
        Input goTest=skipPage.input();
        goTest.type("text");
        goTest.addCssClass("layui-input");
        goTest.value("2");
        Button b=pageContent.button();
        b.addCssClass("layui-laypage-btn");
        b.text("测试");
        b.attr("type","button");
        //跳转栏
        /*
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
        goPage.text("Go");*/
    }
}
