package repl.simple.mathematica.Actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import repl.simple.mathematica.MathSessionWrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;


/**
 * Created by alex on 2/2/14.
 */
public class MathREPLTerminateKernelAction extends MathREPLKernelAction {
    public MathREPLTerminateKernelAction() {
        super();
    }

    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        ToolWindow tw = twm.getToolWindow("Mathematica REPL");

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null !=c && Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : true;

        e.getPresentation().setEnabled(!enabled);
    }

    public void actionPerformed(AnActionEvent e) {

        // TODO: find toolbar, get math wrapper and call method connect with the parameters stored
        // TODO: disable action
        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        statusBarBalloonMsg(e, MessageType.INFO,twm.getActiveToolWindowId());

        ToolWindow tw = twm.getToolWindow("Mathematica REPL");
        final MathSessionWrapper msw =  MathSessionWrapper.adopt(tw.getContentManager().getSelectedContent().getComponent());
        try {
            msw.call("closeLink");
            new Notification("",
                    "JLink",
                    "The connection to the Kernel was stopped.\n"+
                            "To evaluate expression you need to start Kernel again.",
                    NotificationType.INFORMATION).notify( e.getProject() );
            ResourceBundle rb = ResourceBundle.getBundle("repl.simple.mathematica.resources.MathREPLMessages");
            statusBarBalloonMsg(e, MessageType.INFO, rb.getString("kernelStopped"));
            // Change Toolbar appearance (name)
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        Sessions.put(tw.getContentManager().getSelectedContent().getTabName(),true);
    }
}
