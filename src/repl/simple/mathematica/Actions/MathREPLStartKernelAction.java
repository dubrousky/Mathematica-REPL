package repl.simple.mathematica.Actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.ui.LoadingDecorator;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import repl.simple.mathematica.MathREPLBundle;
import repl.simple.mathematica.MathSessionWrapper;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

/**
 * Starts new kernel session in the tab
 */
public class MathREPLStartKernelAction extends MathREPLKernelAction {

    public MathREPLStartKernelAction() {
        super();

    }
    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null!=c&&Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : false;

        e.getPresentation().setEnabled(enabled);

    }

    public void actionPerformed(AnActionEvent e) {

        // get link status
        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));


        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);
        ContentManager cm = tw.getContentManager();
        Content c = cm.getSelectedContent();
        final MathSessionWrapper msw =  MathSessionWrapper.adopt(c.getComponent());
        if(null != msw)
        {
            PropertiesComponent pc = PropertiesComponent.getInstance();
            String args = String.format(pc.getValue("repl.simple.mathematica.mathlink_args"),
                                        pc.getValue("repl.simple.mathematica.mathkernel_path"));

            try {
                ResourceBundle rb = ResourceBundle.getBundle("repl.simple.mathematica.resources.MathREPLMessages");
                msw.call("setLinkArguments", args);
                // Set reasonable timeout to reach the kernel
                msw.call("setConnectTimeout",(int)10000);
                msw.call("connect");
                new Notification("REPL",
                        "JLink",
                        MathREPLBundle.message("connectionEstablished"),
                        NotificationType.INFORMATION).notify(e.getProject());
                Sessions.put(c.getTabName(),false);

            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }

        }

    }
}
