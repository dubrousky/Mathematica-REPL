package repl.simple.mathematica;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.util.PopupUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.popup.PopupFactoryImpl;
import org.jdesktop.swingx.action.ActionManager;

import javax.swing.*;

/**
 * Creates the toolbar for placing the mathematica
 * session tabs. Available by the name 'Mathematica REPL'
 */
public class MathREPLToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        // TODO: Create button panel to control the tabs/session
        //Project currentProject = DataKeys.PROJECT.getData(actionEvent.getDataContext());
        //VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(actionEvent.getDataContext());
        //Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
    }

}
