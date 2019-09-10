package servlet;

import common.SQLHelper;
import common.utils.TextUtils;
import dao.OrderDao;
import dao.ShopCommentDao;
import dao.UserDao;
import model.OrderInfo;
import model.ShopCommentInfo;
import model.UserInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderChangeServlet extends BaseServlet {
    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //订单id
        String orderId = req.getParameter("orderId");
        String type = req.getParameter("type");
        String userId = req.getParameter("userId");
        SQLHelper sqlHelper = new SQLHelper(req);
        OrderDao orderDao = new OrderDao(sqlHelper);

        if ("rec".equals(type)) {
            //确认收货
//            String back = Page.getHomeUrl("Service_order", "Cus_BzServiceOrderForm") + "&" + Page.TAB_NAME + "="
//                    + Cus_BzServiceOrderForm.TYPE_COMPLETED;
//            orderDao.updateOrderState(Page.COMPANYID, orderId, "3");
//            resp.sendRedirect(back);
        } else if ("pay".equals(type)) {
            OrderInfo info = orderDao.getOrderInfo(Page.COMPANYID, orderId);
            String url = "pay?userId="+userId+"&realPay="+info.NowPrice+"&orderId="+orderId;
            //去支付
//            String back = Page.getHomeUrl("Service_order", "Cus_BzServiceOrderForm") + "&" + Page.TAB_NAME + "="
//                    + Cus_BzServiceOrderForm.TYPE_WAIT_REC;
            resp.sendRedirect(url);

        } else if ("comment".equals(type)) {
            StringBuilder files = new StringBuilder();
            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            try {
                //上传图片保存目录
                String upload = req.getServletContext().getRealPath("upload");
                File dir = new File(upload);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                List<FileItem> items = servletFileUpload.parseRequest(req); //解析request请求
                Iterator iter = items.iterator();
                int i = 0;
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) { //如果是表单域 ，就是非文件上传元素
                        String name = item.getFieldName(); //获取name属性的值
                        String value = item.getString(); //获取value属性的值
                        System.err.println(name + "=" + value);
                    } else {
//                        String fieldName = item.getFieldName(); //文件域中name属性的值
                        String fileName = item.getName(); //文件的全路径，绝对路径名加文件名
//                        String contentType = item.getContentType(); //文件的类型
                        long size = item.getSize(); //文件的大小，以字节为单位
                        if (size == 0 || TextUtils.isEmpty(item.getName())) {
                            continue;
                        }
                        fileName = System.currentTimeMillis() + getFileLast(fileName);
                        File saveFile = new File(upload, fileName); //定义一个file指向一个具体的文件
                        item.write(saveFile); //把上传的内容写到一个文件中
                        System.err.println(item.getName() + "->" + saveFile.getAbsolutePath());
                        if (i != 0) {
                            files.append(",");
                        }
                        files.append(fileName);
                        i++;
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            //星数
            ShopCommentInfo shopCommentInfo = new ShopCommentInfo();
            shopCommentInfo.commentLevel = req.getParameter("star-num");
            //评论
            shopCommentInfo.CommentContent = req.getParameter("CommentContent");
            //图片
            shopCommentInfo.CommentImage = files.toString();

            UserDao userDao = new UserDao(sqlHelper);
            UserInfo userInfo = userDao.getUserById(Page.COMPANYID, userId);
            shopCommentInfo.CommentUserID = userId;

            shopCommentInfo.CommentUserName = userInfo == null ? null : userInfo.NickName;
            shopCommentInfo.CommentHeadImg = userInfo == null ? null : userInfo.headImg;

            shopCommentInfo.CommentTIme = new SimpleDateFormat("yyyy-mm-dd").format(new Date(System.currentTimeMillis()));

            OrderInfo info = orderDao.getOrderInfo(Page.COMPANYID, orderId);
            shopCommentInfo.CommentShop = info == null ? null : info.shopID;
            shopCommentInfo.praiseCount = info == null ? null : info.buyCount;
            shopCommentInfo.CommentServiceId = info == null ? null : info.ServiceID;

            //保存评论
            ShopCommentDao shopCommentDao = new ShopCommentDao(sqlHelper);
            shopCommentDao.addComment(Page.COMPANYID, shopCommentInfo);

            //返回订单列表页面
            String back = Page.getHomeUrl("Service_order", "Cus_BzServiceOrderForm") + "&" + Page.TAB_NAME + "=";
                //    + Cus_BzServiceOrderForm.TYPE_ALL;
            resp.sendRedirect(back);
        } else {
            System.err.println("no type:" + type);
        }
    }

    /**
     * 文件的格式
     *
     * @param fileName
     * @return
     */
    private String getFileLast(String fileName) {
        if (fileName == null) {
            return ".jpg";
        }
        int index = fileName.lastIndexOf(".");
        if (index >= 0) {
            return fileName.substring(index);
        }
        return ".jpg";
    }
}
