package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.TableDataCell;
import com.sangupta.htmlgen.tags.body.table.TableRow;
import com.sangupta.htmlgen.tags.head.Script;
import viewtype.ParentView;

import java.util.List;

/**
 * Created by hy on 2017/10/22.
 */
public class ClassifyMenu extends ParentForm {

    public static class MenuInfo {
        public int classifyID;
        public String classifyTitle;
        public int state;
        public String classifyImage;
        public String transferParams;
        public String target;
    }

    List<MenuInfo> mMenuInfos;

    @Override
    protected void loadData(String sql) {
//        super.loadData(sql);
        System.err.println(sql);
        mMenuInfos = sqlHelper.executeQueryList(getCompanyId(), sql, null, MenuInfo.class);
    }

    /**
     * 组装控件生成模板
     */
    @Override
    public HtmlBodyElement<?> createViews() {
        if (mMenuInfos == null) {
            return null;
        }
        Div div = new Div();
        div.id(formName);
        Div swiper = div.div();
        swiper.id(formName + "-swiper");
        swiper.addCssClass("swiper-container swiper-container-horizontal");
        Div content = swiper.div();
        content.addCssClass("swiper-wrapper");

        Div slide = null;
        TBody tBody = null;
        TableRow tr = null;

        int count = mMenuInfos.size();
        for (int i = 0; i < count; i++) {
            if (i % 10 == 0) {
                slide = content.div();
                slide.addCssClass("swiper-slide");
                tBody = slide.table().tbody();
            }
            if (i % 5 == 0) {
                tr = tBody.tr();
            }
            MenuInfo info = mMenuInfos.get(i);
            //单元格内容
            TableDataCell tableRow = tr.td();
            tableRow.attr("width", (100 / 5) + "%");
            tableRow.styles("text-align:center;");
            tableRow.img(ParentView.IMAGE_PRE + info.classifyImage);
            tableRow.p(info.classifyTitle);
            tableRow.attr("onClick", "gotoPage('goto:ServiceScreenPage,Cus_ServiceForm', 'ServiceScreenPage:s.parentId="+info.classifyID+"');");
        }

        Div bottom = swiper.div();
        bottom.id(formName + "-swiper-page");
        bottom.addCssClass("swiper-pagination swiper-pagination-clickable swiper-pagination-bullets");
        bottom.span().addCssClass("swiper-pagination-bullet swiper-pagination-bullet-active");
        int page = count / 10;
        if (page * 10 > count) {
            page++;
        }
        for (int i = 0; i < page; i++) {
            bottom.span()
                    .addCssClass("swiper-pagination-bullet");
        }
        div.add(new Script().text("\n" +
                "    var swiper = new Swiper('#" + formName + "-swiper', {\n" +
                "        pagination: '#" + formName + "-swiper-page',\n" +
                "        paginationClickable: true\n" +
                "    });"));
        return div;
    }

}
