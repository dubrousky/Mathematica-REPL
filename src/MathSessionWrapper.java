import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 2/15/14.
 */
public class MathSessionWrapper {
     // path to the MathLink.jar
     static String jarPath;
     // path to the native MathLnk library
     static String nativePath;
     // path to the MathKernel executable
     static String mathKernelPath;
     // kernel options
     static String[] mathKernelOptions;

     static Map<String,Method> implMethods;
     static Class implClass = null;

     Object implObj;

     MathSessionWrapper(){
         implObj = null;
         try
         {
            implObj = implClass.newInstance();
         }
         catch(InstantiationException e)
         {
              System.out.println(e.getMessage());
         }
         catch(IllegalAccessException e)
         {
             System.out.println(e.getMessage());
         }
     }
     public Object getRootPanel()
     {
         return implObj;
     }

     public void call(String methodName, Object ... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
     {
         ArrayList<Class> paramClasses = new ArrayList<Class>();
         for( Object p : params )
         {
             paramClasses.add(p.getClass());
         }
         Method m = implMethods.get("methodName");
         if( null == m )
         {
             m = implObj.getClass().getMethod(methodName, (Class[]) paramClasses.toArray());
             implMethods.put(methodName,m);
         }
         m.invoke(implObj,params);
     }
     protected static void loadLibrary()
     {
         jarPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/JLink.jar";
         nativePath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX-x86/";
         System.setProperty("java.library.path",nativePath);
         URL myJarFile = null;
         try {
             myJarFile = new URL("file:///"+jarPath);
         } catch (MalformedURLException e) {
             System.out.println(e.getMessage());
             // possibly show message dialog
             e.printStackTrace();
         }
         // Get instance of loader for Mathematica jar library
         URLClassLoader cl = URLClassLoader.newInstance(new URL[]{myJarFile});

         try {
             implClass = cl.loadClass("com.wolfram.jlink.ui.MathSessionPane");
         } catch (ClassNotFoundException e) {
             System.out.println(e.getMessage());
             e.printStackTrace();
         }
     }
}
