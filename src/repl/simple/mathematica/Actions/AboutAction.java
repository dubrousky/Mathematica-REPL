package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import repl.simple.mathematica.MathREPLMessages;

import javax.swing.*;

/**
 * Created by alex on 7/15/14.
 */
public class AboutAction extends AnAction {
    public class MyAboutDialog extends DialogWrapper {
        private JPanel myPanel;
        private JLabel myAboutInfo;
        private Project myProject;
        MyAboutDialog(Project project) {
            super(project,true);
            myAboutInfo.setText(MathREPLMessages.aboutInfo);
            myProject = project;
            init();
        }

        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            return myPanel;
        }

    };
    @Override
    public void actionPerformed(final AnActionEvent anActionEvent) {
        new MyAboutDialog(anActionEvent.getProject()).show();
    }
}
