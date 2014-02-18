import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
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


     MathSessionWrapper(Object o)
     {
        if(null != implClass && o.getClass().isInstance(implClass) )
        {
            implObj = o;
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
         Method m = implMethods.get(methodName);
         if( null == m )
         {
             if( paramClasses.isEmpty() )
             {
                 m = implObj.getClass().getMethod(methodName);
             }
             else
             {
                 // Use the necessary cast to [java.lang.Class
                 Class[] classes = paramClasses.toArray(new Class[0]);
                 m = implObj.getClass().getMethod(methodName,classes);
             }
             if(null != m) implMethods.put(methodName,m);
         }
         if(null != m )
         {
             m.invoke(implObj,params);
         }
         else
         {
             throw new NoSuchMethodException();
         }
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
