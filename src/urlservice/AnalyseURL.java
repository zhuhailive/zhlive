package urlservice;

import entity.Player;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 类方法的反射机制
 * Created by Admini on 2016/12/19.
 */
public class AnalyseURL {


    public static void directMethod(String url,HashMap<String,String> map,OutputStream outputStream){
        String[] classAndMethod = URLMap.URLs.get(url).toString().split("#");
        System.out.println(classAndMethod[0]+"+++++++++++"+classAndMethod[1]);

        try {
            Class cl = Class.forName("urlservice."+classAndMethod[0]);
            Method method = cl.getDeclaredMethod(classAndMethod[1], HashMap.class, OutputStream.class);
            method.invoke(cl.newInstance(),map,outputStream);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}
