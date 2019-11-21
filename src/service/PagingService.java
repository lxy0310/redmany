package service;

import com.sangupta.htmlgen.tags.body.grouping.Div;
import page.Page;

/**
 * 分页服务接口
 */
public interface PagingService {

     /**
      * 给div添加分页的菜单栏
      * @param div 需要添加分页菜单栏的div元素
      * @param page 当前页面对象
      */
     public  void addPagingMenuBar(Div div, Page page);
}
