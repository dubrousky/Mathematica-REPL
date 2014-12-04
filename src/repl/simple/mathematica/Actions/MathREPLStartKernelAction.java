package repl.simple.mathematica.Actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import repl.simple.mathematica.MathSessionWrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

/**
 * Created by alex on 2/2/14.
 */
public class MathREPLStartKernelAction extends MathREPLKernelAction {

    public MathREPLStartKernelAction() {
        super();

    }
    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        ToolWindow tw = twm.getToolWindow("Mathematica REPL");

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null!=c&&Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : false;

        e.getPresentation().setEnabled(enabled);

    }

    public void actionPerformed(AnActionEvent e) {
        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));


        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);
        Content c = tw.getContentManager().getSelectedContent();
        final MathSessionWrapper msw =  MathSessionWrapper.adopt(c.getComponent());
        if(null != msw)
        {
            PropertiesComponent pc = PropertiesComponent.getInstance();
            String args = String.format(pc.getValue("repl.simple.mathematica.mathlink_args"),
                                        pc.getValue("repl.simple.mathematica.mathkernel_path"));

            try {
                ResourceBundle rb = ResourceBundle.getBundle("repl.simple.mathematica.resources.MathREPLMessages");
                msw.call("setLinkArguments",args);
                msw.call("connect");
                new Notification("",
                        "JLink",
                        "The connection to the Kernel was established.",
                        NotificationType.INFORMATION).notify( e.getProject() );
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }

        }
        Sessions.put(c.getTabName(),false);
    }
}
