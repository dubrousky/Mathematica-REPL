package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by alex on 2/2/14.
 */
public class MathREPLStartKernelAction extends MathREPLBaseAction {
    public MathREPLStartKernelAction() {
        super();
    }

    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        //Второй сценарий заключается в обычном вызове метода ToolWindowManager.registerToolWindow() из кода плагина.
        // Этот метод имеет несколько перегрузок, которые могут использоваться в зависимости от ваших задач.
        // Если вы используете перегрузку, которая принимает Swing-компонент, то он становится первой вкладкой,
        // отображаемой в окне инструмента.
    }
}
