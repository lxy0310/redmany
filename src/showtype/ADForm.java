package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Script;
import viewtype.ParentView;

import java.util.Map;

/**
 * Created by hy on 2017/10/21.
 */
public class ADForm extends ParentForm {
//数据库原始配置信息
//        List<Object> view = formFiledDao.getFormContorl(Company_Id, formName, showType, View.class);//拿到所有控件信息
//        List<View> result = new ArrayList<>();
//        if (view != null) {
//            for (int i = 0; i < view.size(); i++) {
//                View v = (View) view.get(i);
//                String str = formFiledDao.getFormContorlAttribute(Company_Id, v.getAttributeId());//查询每个空间的属性
//                v.setAttributeStr(str);
//                result.add(v);
//                break;
//            }
//        }


    @Override
    protected void loadData(String sql) {
        sql = "Select * from adImg_b where AdFormName='" + getFormName() + "';";
        mDatas = sqlHelper.executeQueryList(getCompanyId(), sql, null);
        System.err.println(sql + ":" + mDatas);
    }

    /**
     * 组装控件生成模板
     */
    @Override
    public HtmlBodyElement<?> createViews() {

        if (mDatas == null) {
            return null;
        }

        Div div = new Div();
        div.id(formName);
        Div swiper = div.div();
        swiper.id(formName + "-swiper");
        swiper.addCssClass("swiper-container swiper-container-horizontal");
        Div content = swiper.div();
        content.addCssClass("swiper-wrapper");
        for (Map<String, Object> line : mDatas) {
//            String transferParams = get(line, "transferParams");
//            String serviceid = get(line, "serviceid");
//            if (transferParams != null) {
//                line.put("transferParams", transferParams.replace("{serviceid}", serviceid));
//            }
            Div slide = content.div();
            slide.addCssClass("swiper-slide");
            slide.img(ParentView.IMAGE_PRE + get(line, "imgName"))
                    .onClick(getDataProvider().getOnClick(this, null, line));
        }
        Div bottom = swiper.div();
        bottom.id(formName + "-swiper-page");
        bottom.addCssClass("swiper-pagination swiper-pagination-clickable swiper-pagination-bullets");
        bottom.span().addCssClass("swiper-pagination-bullet swiper-pagination-bullet-active");
        for (int i = 1; i < mDatas.size(); i++) {
            bottom.span()
                    .addCssClass("swiper-pagination-bullet");
        }
        div.add(new Script().text("\n" +
                "    var swiper = new Swiper('#" + formName + "-swiper', {\n" +
                "        iOSEdgeSwipeDetection :true,\n" +
                "        pagination: '#" + formName + "-swiper-page',\n" +
                "        paginationClickable: true\n" +
                "    });"));
        return div;
    }


}
