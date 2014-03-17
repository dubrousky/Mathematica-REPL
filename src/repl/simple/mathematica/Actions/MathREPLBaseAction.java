package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.util.IconLoader;

/**
 * Created by alex on 2/2/14.
 */
public abstract class MathREPLBaseAction extends AnAction {
    static boolean enabled = true;
    public MathREPLBaseAction() {
        super();
        getTemplatePresentation().setIcon(IconLoader.findIcon("/repl/simple/mathematica/resources/icon-run.png"));
    }
    public static void setEnabled(boolean e)
    {
        enabled = e;
    }
}
