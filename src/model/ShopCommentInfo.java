package model;

public class ShopCommentInfo {
   public int Id;
   public String CommentContent;
   public String CommentImage;
   public String CommentTIme;
   public String CommentServiceId;
   public String CommentShop;
   public String commentCount;
   public String commentLevel;
   public String CommentUserID;
   public String CommentUserName;
   public String CommentHeadImg;
   public String praiseCount;

    @Override
    public String toString() {
        return "ShopCommentInfo{" +
                "Id=" + Id +
                ", CommentContent='" + CommentContent + '\'' +
                ", CommentImage='" + CommentImage + '\'' +
                ", CommentTIme='" + CommentTIme + '\'' +
                ", CommentServiceId='" + CommentServiceId + '\'' +
                ", CommentShop='" + CommentShop + '\'' +
                ", commentCount='" + commentCount + '\'' +
                ", commentLevel='" + commentLevel + '\'' +
                ", CommentUserID='" + CommentUserID + '\'' +
                ", CommentUserName='" + CommentUserName + '\'' +
                ", CommentHeadImg='" + CommentHeadImg + '\'' +
                ", praiseCount='" + praiseCount + '\'' +
                '}';
    }
}
