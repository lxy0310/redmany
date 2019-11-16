package test;

public class test2 {


    public static void main(String[] args) {
        String transfer ="UserMsgSet:Id={Id}";
        String str = transfer.substring(transfer.indexOf(":")+1,transfer.length());
        System.out.println(str);
        System.out.println(str.indexOf(","));
        if (str.indexOf(",")>-1){
            String[] strlen =  str.split(",");
            for (int i = 0; i <strlen.length ; i++) {
                String str1 = strlen[i];
                String[] str2 = str1.split("=");
                System.out.println(str2[0]+str2[1]);
            }
        }else {
            String[] str2 = str.split("=");
            System.out.println(str2[0]+str2[1]);
        }
        /*String[] str1 = str.split("=");
        System.out.println(str1[0]+str1[1]);*/

    }

}
