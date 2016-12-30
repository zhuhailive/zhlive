package severside;
/**
 *
 * 主运行类，服务器开启之后会连接腾讯云数据库，服务器占用的是80端口
 * @author keben
 * @Time 2016-11-3
 */

import com.alibaba.fastjson.JSON;
import entity.Player;
import urlservice.AnalyseURL;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyHtmlServer {


    public JSON json;//向客户端返回json 包含ip award
    public String a;//服务器生成的随机数
    public int mark;//标记是否已经转郭转盘

//    BufferedReader br;
    private int querytype=2;
    private String type ="";

    private HashMap<Integer, Player> userHashMap;
    private String postBody;//存放post的内容
    private boolean hasBody = false;//post请求是否包含内容
    HashMap<String,String> postmap;


    public static void main(String[] args) throws IOException {
        int port = 80;
        if (args.length > 0)
            port = Integer.valueOf(args[0]);
        new MyHtmlServer().start(port);

    }

    /**
     * 在指定端口启动http服务器
     *
     * @param port 指定的端口
     * @throws IOException
     */
    public void start(int port) throws IOException {

        new ServerBuffer();
        ServerSocket server = new ServerSocket(port);
        System.out.println("server start at " + port + "...........");
        while (true) {

            Socket client = server.accept();

//            System.out.println(client.getInetAddress().getHostName());


            ServerThread serverthread = new ServerThread(client);
            serverthread.start();


        }
    }

    /**
     * 服务器响应线程，每收到一次浏览器的请求就会启动一个ServerThread线程
     *
     * @author
     */
    class ServerThread extends Thread {
        Socket client;

        public ServerThread(Socket client) {
            this.client = client;
        }

        /**
         * 读取文件内容，转化为byte数组
         *
         * @param filename 文件名
         * @return
         * @throws IOException
         */
        public byte[] getFileByte(String filename) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1000];
            int read;
            while ((read = fis.read(b)) != -1) {
                baos.write(b, 0, read);
            }
            fis.close();
            baos.close();
            return baos.toByteArray();
        }


        /**
         * 分析http请求中的url,分析用户请求的资源，并将请求url规范化
         * 例如请求 "/"要规范成"/index.html","/index"要规范成"/index.html"
         *
         * @param queryurl 用户原始的url
         * @return 规范化的url，即为用户请求资源的路径
         */



        private String getQueryResource(String queryurl) {
            String queryresource = null;
            int index = queryurl.indexOf('?');
            if (index != -1) {
                queryresource = queryurl.substring(0, queryurl.indexOf('?'));
            } else
                queryresource = queryurl;

            int lastindex = queryresource.lastIndexOf("/");


            if (lastindex + 1 == queryresource.length()) {
                queryresource = queryresource + "index.html";
            }
//            else {
//                String filename = queryresource.substring(index + 1);
//                if (!filename.contains("."))
//                    queryresource = queryresource + ".html";
//            }
            return queryresource;

        }


        /**
         * 根据用户请求的资源类型，设定http响应头的信息，主要是判断用户请求的文件类型（html、jpg...）
         *
         * @param queryresource
         * @return
         */
        private String getHead(String queryresource) {
            String filename = "";
            int index = queryresource.lastIndexOf("/");
            filename = queryresource.substring(index + 1);
            String[] filetypes = filename.split("\\.");
            String filetype = filetypes[filetypes.length - 1];


            //添加js，css等http头
            if (filetype.equals("html")) {
                return "HTTP/1.0 200 OK\n" + "Content-Type:text/html\n" + "Server:myserver\n" + "\n";
            } else if (filetype.equals("ico")) {
                return "HTTP/1.0 200 OK\n" + "Content-Type:image/x-icon\n" + "Server:myserver\n" + "\n";
            } else if (filetype.equals("js")) {
                return "HTTP/1.0 200 OK\n" + "Content-Type:application/x-javascript\n" + "Server:myserver\n" + "\n";
            } else if (filetype.equals("jpg") || filetype.equals("gif") || filetype.equals("png")) {
                return "HTTP/1.0 200 OK\n" + "Content-Type:image/jpeg\n" + "Server:myserver\n" + "\n";
            } else if (filetype.equals("png")) {
                return "HTTP/1.0 200 OK\n" + "Content-Type:image/png\n" + "Server:myserver\n" + "\n";
            } else if (filetype.equals("css")) {
                return "HTTP/1.0 200 OK\n" + "Content-Type:text/css\n" + "Server:myserver\n" + "\n";

            //get 与 post 也需要http头
            } else return "HTTP/1.0 200 OK\n" + "Content-Type:text\n" + "Server:myserver\n" + "\n";

        }

        //读取post请求内容


        private  HashMap<String,String> getRequestBody(BufferedReader br) throws IOException{

            HashMap<String,String> posthm = new HashMap<>();

            postBody = "";
            int a;
            while((a = br.read())!=41){
                postBody += (char)a;
            }
//            System.out.println(postBody);
            System.out.println("提交参数：");
            Pattern pt = Pattern.compile("(.*?)=(.*?);");
            Matcher matcher = pt.matcher(postBody);
            while(matcher.find()){
                posthm.put(matcher.group(1),matcher.group(2));
                System.out.println(matcher.group(1)+"="+matcher.group(2)+"  ");
            }
            return posthm;
        }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
//         JSON.toJSONString(HashMap)用的是 op.write(("{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(returnhm) + "}").getBytes()); Dictionary
//        JSON.toJSONString(模型类：player)op.write("{\"c2array\":true,\"size\":["+ goods_house.getGoods_contains() +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(opstr) +"}").getbytes   Array
//    public void text(OutputStream op){
//
//        HashMap<String ,String > hashMap =new HashMap<>();
//        hashMap.put("player_cash","10");
//        hashMap.put("","");
//
//
//        String data =null;
//
//        data = "{\"c2dictionary\":true,\"data\":" + JSON.toJSONString(hashMap) + "}";
//        try {
//            op.write(data.getBytes());
//            op.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public void text1(OutputStream op){
//        String[][][] a =new String[1][4][2];
//        int i = 0;
//        a[i][0][0] = "goods_id";
//        a[i][1][0] = "goods_price";
//        a[i][2][0] = "goods_amount";
//        a[i][3][0] = "goods_name";
//
//        a[i][0][1] = "1";
//        a[i][1][1] = "10";
//        a[i][2][1] = "50";
//        a[i][3][1] = "林光雄";
//
//        String data = "{\"c2array\":true,\"size\":["+ i+1 +","+ 4 +","+ 2 +"],\"data\":"+ JSON.toJSONString(a) +"}";
//        try {
//            op.write(data.getBytes());
//            op.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//    }
        @Override
        public void run() {
           ;
            InputStream is;
            try {


                is = client.getInputStream();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                String aa;

                int readint;
                char c;
                byte[] buf = new byte[1000];
                OutputStream os = client.getOutputStream();
//                client.setSoTimeout(100);
                byte[] data = null;
                String cmd = "";
                String queryurl = "";
                int state = 0;
                String queryresource;
                String head;


                try {



                    //请求头

                    String i = br.readLine();
                    String j[] = i.split(" ");
                    queryurl = j[1];
                    cmd = j[0];
                      System.out.println(i);





                    //判断是否包含请求内容并将请求头读完！

                    String temp ="";
                    while ((temp = br.readLine())!=null) {

                        if(temp.equals(""))
                            break;
                        else if(temp.contains("Content-Length:"))
                            hasBody = true;
                        System.out.println(temp);

                    }


                    //如果包含请求内容，读取请求内容

                    if(hasBody){
                        postmap = getRequestBody(br);
                    }


                    //请求的文件

//                    System.out.println("11111111111111111." + queryurl);
                    queryresource = getQueryResource(queryurl);
                    System.out.println("请求url" + queryresource);

                    //判断是get post 请求文件
                    if(queryresource.contains("."))
                        querytype=2;
                    else if(cmd.equals("GET"))
                        querytype=0;
                    else if(cmd.equals("POST"))
                        querytype=1;

                    //添加http头

                    head = getHead(queryresource);

                    os.write(head.getBytes("utf-8"));

                    //匹配url
                    System.out.println("类型："+querytype);
                    if(querytype!=2)
                        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                         AnalyseURL.directMethod(queryresource,postmap,os);
//                        text1(os);


                    else {
                        data = getFileByte("webapp" + queryresource);
                        os.write(data);
                    }


                        os.flush();
                        os.close();


                }catch (Exception e){
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


}