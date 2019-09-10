package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.table.TableDataCell;
import com.sangupta.htmlgen.tags.body.table.TableRow;
import com.sangupta.htmlgen.tags.body.text.Span;
import common.utils.DataHelper;
import viewtype.ParentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/10/22.
 */
public class ClassifyAdvertisingListForm extends ParentForm {
    @Override
    protected void loadData(String sql) {
        super.loadData(sql);
    }

    /**
     * 组装控件生成模板
     */
    @Override
    public HtmlBodyElement<?> createViews() {
        Map<Integer, List<GroupShop>> shops = getGroupShop();

        Div div = new Div();
        div.id(formName);
        for (Map.Entry<Integer, List<GroupShop>> e : shops.entrySet()) {
            Div lineDiv = new Div();
            lineDiv.addCssClass("shop-item-line");
            div.add(lineDiv);
            Div shop = div.div();
            shop.addCssClass("shop-item");
            shop.add(hr());
            Div title = shop.div();
            title.addCssClass("shop-top");
            Div tleft = title.div();
            tleft.addCssClass("shop-item-img-line");

            Span left = title.span();
            left.addCssClass("shop-group-name");
            left.text(e.getValue().get(0).groupField);
            Span right = title.span();
            right.addCssClass("shop-group-more");
            right.text("更多 >");
            right.onClick("gotoPage('goto:GoodsListPage,listForm','GoodsListPage:p.category_id="+e.getValue().get(0).groupFieldID+"');");
//            right.onClick("gotoPage('goto:Bzservice_SelectPage,copForm','ServiceScreenPage:s.parentId="+e.getValue().get(0).groupFieldID+"');");
            //GoodsListPage:p.category_id={groupFieldID}
            shop.table(table(e.getValue()));
        }
        return div;
    }

    private Div hr() {
        Div bgdiv = new Div();
        bgdiv.id("sep");
        return bgdiv;
    }

    private Table table(List<GroupShop> groupShops) {
        Table table = new Table();
        table.addCssClass("shop-table");
        //假设数据有15条
        int count = groupShops.size();
        //每行多少个
        //table添加内容
        TBody tBody = table.tbody();
        TableRow tr = tBody.tr();
        //遍历
        for (int i = 0; i < count; i++) {
            GroupShop data = groupShops.get(i);
            //单元格内容
            TableDataCell tableRow = tr.td();
            tableRow.addCssClass("shop-srv");
            //groupFieldID,id
//            goto:product_detail,copForm[^]silenceSubmit:My_track,newForm,$$Product_id={Id}
//            product_detail_spec:p.Id={Id}[^]product_describe_free:p.Id={Id}[^]globalVariable:productId={Id}
            tableRow.onClick("gotoPage('goto:product_detail,copForm[^]silenceSubmit:My_track,newForm,$$Product_id="+data.Id+"','product_detail_spec:p.Id="+data.Id+"[^]product_describe_free:p.Id="+data.Id+"[^]globalVariable:productId="+data.Id+"');");
//            tableRow.onClick("gotoPage('goto:product_detail,copForm','product_detail_spec:p.Id="+data.Id+"[^]product_describe_free:p.Id="+data.Id+"');");
//            tableRow.onClick("gotoPage('goto:ServiceDetailsPage,Cus_ServiceDetailsForm','ServiceDetailsPage:Id="+data.Id+"');");
            tableRow.img(ParentView.IMAGE_PRE + data.serveImage).addCssClass("shop-img");
            tableRow.div().addCssClass("shop-srv-name")
                    .text(data.name);
            Div p = tableRow.div();
            p.addCssClass("shop-price");
            p.span(data.sum).addCssClass("shop-sum");
            Div bottom = tableRow.div();
            bottom.addCssClass("shop-bottom");
//            bottom.span().img(ParentView.IMAGE_PRE + data.shopicon).addCssClass("shop-icon");
            bottom.span().text(data.sku_name).addCssClass("sku_name");
        }
        return table;
    }

    private Map<Integer, List<GroupShop>> getGroupShop() {
        if (mGroupList != null) {
            return mGroupList;
        }
        mGroupList = new HashMap<>();
        if (mDatas != null) {
            for (Map<String, Object> item : mDatas) {
                GroupShop groupShop = new GroupShop();
                groupShop.Id = DataHelper.getInt(item, "Id");
                groupShop.state = DataHelper.getInt(item, "state");
                groupShop.groupFieldID = DataHelper.getInt(item, "groupFieldID");
                groupShop.groupField = DataHelper.get(item, "groupField");
                groupShop.serveImage = DataHelper.get(item, "serveImage");
                groupShop.name = DataHelper.get(item, "name");
                groupShop.shopicon = DataHelper.get(item, "shopicon");
                groupShop.sku_name = DataHelper.get(item, "sku_name");
                groupShop.sum = DataHelper.get(item, "sum");
                List<GroupShop> list = mGroupList.get(groupShop.groupFieldID);
                if (list == null) {
                    list = new ArrayList<>();
                    mGroupList.put(groupShop.groupFieldID, list);
                }
                if (list.size() < 3) {
                    list.add(groupShop);
                }
            }
        }
        return mGroupList;
    }

    private Map<Integer, List<GroupShop>> mGroupList;

    private static class GroupShop {
        private int Id;
        private int state;
        private int groupFieldID;
        private String groupField;
        private String serveImage;
        private String name;
        private String sum;
        private String shopicon;
        private String sku_name;

        public GroupShop() {
        }

        @Override
        public String toString() {
            return "GroupShop{" +
                    "id=" + Id +
                    ", state=" + state +
                    ", groupFieldID=" + groupFieldID +
                    ", groupField='" + groupField + '\'' +
                    ", serveImage='" + serveImage + '\'' +
                    ", name='" + name + '\'' +
                    ", sum=" + sum +
                    ", shopicon='" + shopicon + '\'' +
                    ", sku_name='" + sku_name + '\'' +
                    '}';
        }
    }
}
