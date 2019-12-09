package test;

/**
 * 测试类
 */
public class test {

//    public static XMPPConnection xmpp_conn;
//    public static ConnectionConfiguration xmpp_conf;

    // 服务器IP
    public static String xmpp_ip = new String("193.112.244.24");
    // 服务器名
    public static String xmpp_host = new String("oa.redmany.com");
    // 服务器端口
    public static int xmpp_port = 5222;

    // 用户名和密码
    public static String user = new String("malltakeouts_cs001");
    public static String pass = new String("123");

    // 连接服务器
//    public static void conn(){
//        try{
//            XMPPConnection.DEBUG_ENABLED = true;
//            // 配置连接
//            xmpp_conf = new ConnectionConfiguration(xmpp_ip, xmpp_port);
////            xmpp_conf.setReconnectionAllowed(true);
////            xmpp_conf.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
////            xmpp_conf.setSASLAuthenticationEnabled(false);
////            xmpp_conf.setCompressionEnabled(false);
//
//            // 连接，并根据用户名和密码登录
//            xmpp_conn = new XMPPConnection(xmpp_conf);
//            xmpp_conn.DEBUG_ENABLED = true;
//            xmpp_conn.connect();
//            xmpp_conn.login(user, pass);
//
//            // 获取相关变量
//            String tmp;
//            tmp = xmpp_conn.getConnectionID();
//            System.out.println("ConnectionID:" + tmp);
//            tmp = xmpp_conn.getHost();
//            System.out.println("Host:" + tmp);
//            tmp = xmpp_conn.getServiceName();
//            System.out.println("ServiceName:" + tmp);
//            tmp = xmpp_conn.getUser();
//            System.out.println("User:" + tmp);
//        }
//        catch (XMPPException e){
//            System.out.println("Error:" + e.toString());
//        }
//    }


//    public void CreatUser(String Msg){
//        System.out.println("服务器已经进入了CreatUser方法...");
//        String name = this.getType(Msg, "name");
//        String password = this.getType(Msg, "psw");
//        String tellphone = this.getType(Msg, "tel");
//        String email = this.getType(Msg, "email");
//        System.out.println(name+"  "+password+"  "+tellphone+"  "+email);
//        UserDatapojo udp = new UserDatapojo(2,name,password,tellphone,email);
//        try {
//            new UserDatadaoimpl().insert(udp);
//            System.out.println("数据已经存进数据库...");
//            oos.writeUTF("用户注册成功...");
//            oos.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }}

//    public void run() {
//        try {
//            String Msg = null;
//            while (true) {
//                Msg = ois.readUTF();
//                String type = this.getType(Msg, "type");
//                if ("createUser".equals(type)) {
//                    this.CreatUser(Msg);
//                } else if ("loginUser".equals(type)) {
//                    this.LoginUser(Msg);
//                }
//                Msg = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void actionPerformed(ActionEvent e) {
//        if("注册".equals(e.getActionCommand())){
//            String type = "<type>createUser</type>";
//            String name = "<name>"+nameField.getText()+"</name>";
//            String psw = "<psw>"+pswField.getText()+"</psw>";
//            String tel = "<tel>"+telField.getText()+"</tel>";
//            String email = "<email>"+mailField.getText()+"</email>";
//            String CreatUserMsg = "<msg>"+type+name+psw+tel+email+"</msg>";
//            System.out.println(CreatUserMsg);
//            Client socket = Client.getInstance();
//            socket.getConnection();
//            System.out.println("客户端已连接上服务器...");
//            try {
//                socket.getOos().writeUTF(CreatUserMsg);
//                socket.getOos().flush();
//                System.out.println("已发送XMPP协议给服务器...");
//                System.out.println(socket.getOis().readUTF());;
//                socket.closeConnection();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//            System.out.println("执行了注册功能..");
//        }}


    public String getType(String str,String type) {//处理字符串
        System.out.println("已经进入了getType方法，获取的Msg为:   " + str);
        int beginIndex = str.indexOf("<" + type + ">");
        int endIndex = str.indexOf("</" + type + ">");
        String value = str.substring(beginIndex + 2 + type.length(), endIndex);
        System.out.println("获取的value是:   " + value);
        return value;
    }

    public static void main(String[] args) {
//        conn();
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {

        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

}

