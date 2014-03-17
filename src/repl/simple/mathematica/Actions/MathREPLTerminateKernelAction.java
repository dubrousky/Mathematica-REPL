package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import repl.simple.mathematica.MathSessionWrapper;

/**
 * Created by alex on 2/2/14.
 */
public class MathREPLTerminateKernelAction extends MathREPLBaseAction {
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
//        try {
//            msw.call("getKernelLink");
//        } catch (NoSuchMethodException e1) {
//            e1.printStackTrace();
//        } catch (IllegalAccessException e1) {
//            e1.printStackTrace();
//        } catch (InvocationTargetException e1) {
//            e1.printStackTrace();
//        }
        enabled = true;
    }
}
