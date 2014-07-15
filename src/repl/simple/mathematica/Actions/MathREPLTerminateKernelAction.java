package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import repl.simple.mathematica.MathREPLMessages;
import repl.simple.mathematica.MathSessionWrapper;

import java.lang.reflect.InvocationTargetException;


/**
 * Created by alex on 2/2/14.
 */
public class MathREPLTerminateKernelAction extends MathREPLKernelAction {
    public MathREPLTerminateKernelAction() {
        super();
    }

    public void update(AnActionEvent e)
    {
        e.getPresentation().setEnabled(!enabled);
    }

    public void actionPerformed(AnActionEvent e) {

        // TODO: find toolbar, get math wrapper and call method connect with the parameters stored
        // TODO: disable action
        MathSessionWrapper msw = MathSessionWrapper.getSingleton();
        try {
            msw.call("closeLink");
            balloonMessage(e, MessageType.INFO , MathREPLMessages.kernelStopped);
            // Change Toolbar appearance (name)
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        enabled = true;
    }
}
