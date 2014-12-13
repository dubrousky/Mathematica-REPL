/*
    Mathematica REPL IntelliJ IDEA plugin
    Copyright (C) 2014  Aliaksandr Dubrouski

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package repl.simple.mathematica;

import com.intellij.ide.util.PropertiesComponent;

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
    public static MathSessionWrapper create() {
        try {
            loadImplementationClass();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
            if (null != implClass)
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
            return m.invoke(implObj, params);
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
    public void call(String methodName, Object ... params) throws NullPointerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        ArrayList<Class> paramClasses = new ArrayList<Class>();
        for( Object p : params )
        {
            if(isWrapperType(p.getClass()))
                paramClasses.add(getMaybePrimitiveClass(p.getClass()));
            else
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
     * Loads the com.wolfram.jlink.ui.MathSessionPane class at run time
     * using URLClassLoader from the system path.
     */
    private static void loadImplementationClass() throws ClassNotFoundException, MalformedURLException {
        PropertiesComponent pc = PropertiesComponent.getInstance();
        if( null == implClass ) {

            String jarPath = pc.getValue("repl.simple.mathematica.mathlink_path");

            String nativePath = pc.getValue("repl.simple.mathematica.native_library_path");

            System.setProperty("com.wolfram.jlink", nativePath);

            URL myJarFile = new URL("file:////" + jarPath);

            // Get instance of loader for Mathematica jar library
            URLClassLoader cl = URLClassLoader.newInstance(new URL[]{myJarFile});


            implClass = cl.loadClass("com.wolfram.jlink.ui.MathSessionPane");

        }
    }

    public boolean hasImplementation() {
        return null != implClass;
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return clazz.equals(Boolean.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(Byte.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Float.class);
    }

    public static Class getMaybePrimitiveClass(Class<?> clazz) {
        if(clazz.equals(Boolean.class))
            return boolean.class;
        else if(clazz.equals(Integer.class))
            return int.class;
        else if( clazz.equals(Character.class))
            return int.class;
        else if(clazz.equals(Byte.class))
            return byte.class;
        else if(clazz.equals(Short.class))
            return short.class;
        else if(clazz.equals(Double.class))
            return double.class;
        else if(clazz.equals(Long.class))
            return long.class;
        else if(clazz.equals(Float.class))
            return float.class;
        else
            return clazz.getClass();
    }
}
