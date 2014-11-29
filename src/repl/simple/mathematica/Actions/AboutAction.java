package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ResourceBundle;

/**
 * This action shows the information form about the plugin
 */
public class AboutAction extends MathREPLBaseAction {
    /**
     * Class constructor binds
     */
    public class MyAboutDialog extends DialogWrapper {
        private JPanel myPanel;
        private JLabel myAboutInfo;
        private Project myProject;
        MyAboutDialog(Project project) {
            super(project,true);
            ResourceBundle rb = ResourceBundle.getBundle("repl.simple.mathematica.resources.MathREPLMessages");
            myAboutInfo.setText(rb.getString("aboutInfo"));
            myProject = project;
            init();
        }

        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            return myPanel;
        }

    };

    /**
     * Shows the information dialog with plugin info
     * @param anActionEvent
     */
    @Override
    public void actionPerformed(final AnActionEvent anActionEvent) {
        new MyAboutDialog(anActionEvent.getProject()).show();
    }
}
