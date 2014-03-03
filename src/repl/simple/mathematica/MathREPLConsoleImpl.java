package repl.simple.mathematica;
/**
 * Created by alex on 2/2/14.
 */

import com.intellij.openapi.diagnostic.Logger;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;


public class MathREPLConsoleImpl {
    private static final Logger LOG = Logger.getInstance("#" + MathREPLConsoleImpl.class.getName());
    private static final int SEPARATOR_THICKNESS = 1;


    public void run(String[] argv) {
        System.setProperty("java.library.path","/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX-x86/");



        ///////////////////////////////////////

        //Load math session pane class
        URLClassLoader mathSessionPaneLoader;

        //MathSettings ms = MathSettings.getInstance();

        // get the path to the JLink.jar
        //String filePath = ms.getMathLinkPath();

        MathSessionWrapper.loadLibrary();
        MathSessionWrapper msw = MathSessionWrapper.getSingleton();


        // Create the frame window to hold the pane.
        Frame frm = new Frame();
        frm.setSize(500, 500);
        frm.add((JScrollPane)msw.getRootPanel());
        frm.setVisible(true);
        frm.doLayout();


        try {
            msw.call("setLinkArguments", "-linkmode launch -linkname \"/Applications/Mathematica.app/Contents/MacOS/MathKernel\" -mathlink");
            msw.call("connect");
            msw.call("setSyntaxColoring",(boolean)true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
