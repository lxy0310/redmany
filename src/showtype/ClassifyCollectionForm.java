package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.CommonUtils;
import model.CategoryInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2018/1/23.
 */
public class ClassifyCollectionForm extends CustomForm {

    private List<CategoryInfo> mCategoryInfo = new ArrayList<>();
    private Map<Integer, List<CategoryInfo>> mItemMap=new HashMap<>();
    //外面包裹的大的div
    Div bDiv = new Div();
    private String imgUrl = CommonUtils.getFileData;




    @Override
    protected void loadData(String sql) {
        super.loadData(sql);
        mDatas = getDatas();
        System.out.println("分类sql>>>>>>>>>>"+sql.toString());
//        System.out.println("分类datas>>>>>>>>>>"+getDatas().toString());
        System.out.println("分类mData>>>>>>>>>>"+mDatas.toString());


    }

    @Override
    public HtmlBodyElement createView() {
        return  makeContent();
    }

    protected Div makeContent() {
        bDiv.style("width","100%").style("margin-top","50px");

//        List<View> views = getViews();
//        String html = getHtmlTemplate();
//        List<String> list = new ArrayList<>();
//        for (View view : views) {
//            html = makeViews(list, view, null, html);
//        }
//
//        if (!TextUtils.isEmpty(html)) {
//            div.text(html);
//        }
//        for (String v : list) {
//            div.text(v);
//        }


        if(mDatas!=null && mDatas.size()>0){
            for(int i=0;i<mDatas.size();i++){
                CategoryInfo categoryInfo = new CategoryInfo();
                categoryInfo.Id = (int) mDatas.get(i).get("Id");
                categoryInfo.name = (String) mDatas.get(i).get("name");
                categoryInfo.icon = (String) mDatas.get(i).get("icon");
                if(mDatas.get(i).get("parentID")==null){
                    continue;
                }else{
                categoryInfo.parentID = (int) mDatas.get(i).get("parentID");
                }
                categoryInfo.adImg = (String) mDatas.get(i).get("adImg");
                int parentId = categoryInfo.getParentID();
                if(parentId == 0){
                    mCategoryInfo.add(categoryInfo);
                }else{
                    List<CategoryInfo> list = mItemMap.get(parentId);
                    if(list == null){
                        list = new ArrayList<>();
                        mItemMap.put(parentId, list);
                    }
                    list.add(categoryInfo);
                }
            }

        }
        System.out.println("listmap="+mItemMap);

        if(mCategoryInfo==null){
            return new Div().text("商城暂无商品");
        }else{
            System.out.println("分类>>>>>>>>>>>>"+mCategoryInfo.toString());
        }

//上方搜索栏div
        Div tDiv = bDiv.div();
//        Img img = tDiv.img(getDataProvider().getImageUrl(this,get));

        tDiv.id(formName+"-tItem");


        //左方导航div
        Div lDiv = bDiv.div();
        lDiv.id(formName+"-lItem");

        //右方二级详情div
        Div rDiv =bDiv.div();
        rDiv.id(formName+"-rItem");
        boolean first = true;
        for(CategoryInfo info : mCategoryInfo){
            Div item = lDiv.div();
            item.addCssClass("menu");
            item.text(info.getName());
            item.id("menu"+info.getId()).onClick("$('.item').hide();$('#item"+info.getId()+"').show();$(this).css({background:'#000'}).siblings().css({background:'#252829'})");
//            Img img = bDiv.img(imgUrl+info.getAdImg());

            Div subItem = rDiv.div();
            subItem.addCssClass("item");
            subItem.id("item"+info.getId());
            //TODO 二级div的内容
            List<CategoryInfo> list = mItemMap.get(info.getId());
            if(list==null||list.size()==0){
                subItem.text("没有内容");//可以注释，也可以显示一个图片
            }else{
//                Img img = subItem.span().img(imgUrl+info.getIcon()).addCssClass("sub-title-img");
                Div itemlist = subItem.div();
                for(CategoryInfo sub : list){
                    Div sitem = itemlist.div();
                    sitem.id("subitem"+sub.getId());
                    sitem.addCssClass("sub-item");
                    sitem.img(imgUrl+sub.getIcon()).addCssClass("sub-img");
                    sitem.label(sub.getName()).addCssClass("sub-title");
                    sitem.onClick("");//点击事件
                }
            }
            if(first){
                first=false;
            }else{
                subItem.styles("display:none;");
            }


        }
        return bDiv;
    }
}
