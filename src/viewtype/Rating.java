package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;

public class Rating extends ParentView {
    @Override
    public String getType() {
        return "Rating";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        //<input id="input-id" type="number" class="rating" min=0 max=5 step=0.5 data-size="lg" >
        int xx = Integer.parseInt(super.getData("rating"));
        Div star_div = new Div();
        star_div.id(getName());
        star_div.input("hidden", "4").idAndName("star-num");
        Div star = star_div.div().styles("display:inline-block");
        int max = 5;
        for (int i = 0; i < max; i++) {
//            if (i < level) {
//                star.img("images/star2.png").addCssClass("star");//.onClick("");
//            } else {
            if(xx > 0){
                star.img("images/star2.png").addCssClass("star");//.onClick("");
                --xx;
            }
            else
                star.img("images/star1.png");
//            }
        }
        star_div.script("function chooseStar(index){\n" +
                "   for(var i=0;i<5;i++){\n" +
                "      if(i<index){\n" +
                "         $('#star-'+i).attr('src', 'images/star2.png');" +
                "      }else{\n" +
                "         $('#star-'+i).attr('src', 'images/star1.png');\n" +
                "      }\n" +
                "   }\n" +
                "   $('#star-num').val(''+index);\n" +
                "}");
        star_div.script("chooseStar(3);");
        return star_div;
    }
}
