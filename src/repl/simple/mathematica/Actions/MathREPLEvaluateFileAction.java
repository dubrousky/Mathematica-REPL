package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

/**
 * Created by alex on 3/18/14.
 */
public class MathREPLEvaluateFileAction extends MathREPLKernelAction {
    // TODO: action should be available for running session only
    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        // FIXME: move tool window name to the base class
        ToolWindow tw = twm.getToolWindow("Mathematica REPL");

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null!=c&&Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : true;

        e.getPresentation().setEnabled(!enabled);

    }
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here:
        // Open file selection, read through the link

        // Wait for response

    }
}
