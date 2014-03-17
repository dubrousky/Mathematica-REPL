package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import repl.simple.mathematica.MathSessionWrapper;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by alex on 2/2/14.
 */
public class MathREPLStartKernelAction extends MathREPLBaseAction {

    public MathREPLStartKernelAction() {
        super();

    }
    public void update(AnActionEvent e)
    {
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
        enabled = false;
        MathSessionWrapper msw = MathSessionWrapper.getSingleton();
        if(null != msw)
        {
            String args = "-linkmode launch -linkname \"/Applications/Mathematica.app/Contents/MacOS/MathKernel\" -mathlink";

            try {
                msw.call("setLinkArguments",args);
                msw.call("connect");
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
