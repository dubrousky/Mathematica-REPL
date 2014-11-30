package repl.simple.mathematica.Actions;

import com.intellij.ide.util.PropertiesComponent;
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
        // TODO: insert action logic here
        //Второй сценарий заключается в обычном вызове метода ToolWindowManager.registerToolWindow() из кода плагина.
        // Этот метод имеет несколько перегрузок, которые могут использоваться в зависимости от ваших задач.
        // Если вы используете перегрузку, которая принимает Swing-компонент, то он становится первой вкладкой,
        // отображаемой в окне инструмента.
        // TODO: find toolbar, get math wrapper and call method connect with the parameters stored
        // TODO: disable action and enable terminate

        // TODO: get link status
        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));

        //statusBarBalloonMsg(e, MessageType.INFO,twm.getActiveToolWindowId());

        ToolWindow tw = twm.getToolWindow("Mathematica REPL");
        Content c = tw.getContentManager().getSelectedContent();
        final MathSessionWrapper msw =  MathSessionWrapper.adopt(c.getComponent());
        if(null != msw)
        {
            PropertiesComponent pc = PropertiesComponent.getInstance();
            String args = String.format(pc.getValue("repl.simple.mathematica.mathlink_args"),
                                        pc.getValue("repl.simple.mathematica.mathkernel_path"));
            // TODO: use configured values
            try {
                ResourceBundle rb = ResourceBundle.getBundle("repl.simple.mathematica.resources.MathREPLMessages");
                msw.call("setLinkArguments",args);
                msw.call("connect");
                statusBarBalloonMsg(e, MessageType.INFO, rb.getString("kernelStarted"));
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
