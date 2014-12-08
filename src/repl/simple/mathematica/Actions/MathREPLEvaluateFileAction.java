package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import repl.simple.mathematica.MathSessionWrapper;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Allows to select file and then load into the repl.
 */
public class MathREPLEvaluateFileAction extends MathREPLKernelAction {
    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));

        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null!=c&&Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : true;

        e.getPresentation().setEnabled(!enabled);

    }
    public void actionPerformed(AnActionEvent e) {
        // Open file selection, read through the link
        Frame f = new Frame();
        f.setSize(500, 500);
        // get path for JLink java library
        FileDialog fd = new FileDialog(f, "Path to the package file", FileDialog.LOAD);
        //get current file as starting point
        fd.setDirectory(e.getProject().getBasePath());
        fd.setFile("*.m");
        fd.setVisible(true);
        final String path = fd.getDirectory()+fd.getFile();
        // Wait for response
        ToolWindow tw = null;

        tw = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext())).getToolWindow(TOOL_WINDOW);


        final MathSessionWrapper msw =  MathSessionWrapper.adopt(tw.getContentManager().getSelectedContent().getComponent());
        JScrollPane c = (JScrollPane) msw.getRootPanel();
        JTextPane j = null;
        try {
            j = (JTextPane)c.getClass().getMethod("getTextPane").invoke(c);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        if(null!=j) {
            final JTextPane jc = j;
            // On some platforms (e.g., Linux) we need to explicitly give the text pane the focus
            // so it is ready to accept keystrokes. We want to do this after the pane has finished
            // preparing itself (which happens on the Swing UI thread via invokeLater()), so we
            // need to use invokeLater() ourselves.
            ApplicationManager.getApplication().invokeLater(
                    new Runnable() {
                        public void run() {
                            jc.requestFocus();
                            try {
                                jc.getDocument().insertString(jc.getDocument().getLength(), "<<" + path + ";", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            try {
                                msw.call("evaluateInput");
                            } catch (NoSuchMethodException e1) {
                                e1.printStackTrace();
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            } catch (InvocationTargetException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
            );
        }
    }
}
