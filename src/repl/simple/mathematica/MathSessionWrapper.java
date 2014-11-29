package repl.simple.mathematica;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class wraps the JLink functionality and proxies
 * the requests from ide to the Mathematica Kernel and decouples
 * JLink compile time dependency.
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

    public static Class getImplClass() {
        return implClass;
    }

    static Class implClass = null;

     Object implObj;

    /**
     * This method constructs and returns the instance of the wrapper
     * object
     * @return
     */
    public static MathSessionWrapper create(){
        loadLibrary();
        return new MathSessionWrapper();
    }

    public static MathSessionWrapper adopt(Object o) {
        if( implClass.isInstance(o) ) {
            return new MathSessionWrapper(o);
        }
        return null;
    }
    private MathSessionWrapper(Object o)
    {
        implObj = o;
    }
    /**
     * Class constructor
     */
    private MathSessionWrapper(){
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
        // TODO: Add listener to terminate kernel
    }

     public Object getRootPanel()
     {
         return implObj;
     }

    /**
     * The method sets the string s as an input in the repl
     * @param s String to be placed into the repl
     */
    public void setInput(String s)
    {
        Method m = null;
        try {
            m = implObj.getClass().getMethod("getTextPane");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if(null!=m)
        {
            JTextPane t = null;
            try {
                t = (JTextPane) m.invoke(implObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if(null!=t)
            {
                final JTextPane j = t;
                final String cmd = s;
                // TODO: replace with ide implementation
                // On some platforms (e.g., Linux) we need to explicitly give the text pane the focus
                // so it is ready to accept keystrokes. We want to do this after the pane has finished
                // preparing itself (which happens on the Swing UI thread via invokeLater()), so we
                // need to use invokeLater() ourselves.
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        j.requestFocus();
                        try {
                            j.getDocument().insertString(j.getDocument().getLength(), "\n"+cmd, null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        }
    }

    /**
     * The method applies the method of the JLink package with name
     * methodName to the list of Object parameters using the reflection
     * @param methodName Name of the method to be called
     * @param params Vararg parameter list
     * @return Returns the result of the method invocation
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object apply(String methodName, Object ... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        ArrayList<Class> paramClasses = new ArrayList<Class>();
        for( Object p : params )
        {
            paramClasses.add(p.getClass());
        }
        Method m = null;//implMethods.get(methodName);
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
            //if(null != m) implMethods.put(methodName,m);
        }
        if( null != m )
        {
            return m.invoke(implObj,params);
        }
        else
        {
            throw new NoSuchMethodException();
        }
    }

    /**
     *
     * @param methodName
     * @param params
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void call(String methodName, Object ... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        ArrayList<Class> paramClasses = new ArrayList<Class>();
        for( Object p : params )
        {
         paramClasses.add(p.getClass());
        }
        Method m = null;//implMethods.get(methodName);
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
            //if(null != m) implMethods.put(methodName,m);
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

    /**
     *
     */
    public static void loadLibrary( )
    {
        if( null == implClass )
        {
            // FIXME: use configuration to determine paths
            jarPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/JLink.jar";
            nativePath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX-x86-64/";

            //System.setProperty("java.library.path",nativePath);
            System.setProperty("com.wolfram.jlink",nativePath);

            URL myJarFile = null;
            try {
                myJarFile = new URL("file:////"+jarPath);
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
}
