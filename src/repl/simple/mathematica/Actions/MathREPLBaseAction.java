package repl.simple.mathematica.Actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

/**
 * Created by alex on 2/2/14.
 */
public abstract class MathREPLBaseAction extends AnAction {
    public MathREPLBaseAction() {
        super();
        getTemplatePresentation().setIcon(IconLoader.findIcon("/repl/simple/mathematica/resources/icon-run.png"));
    }

    public void balloonMessage(AnActionEvent e,com.intellij.openapi.ui.MessageType messageType, String htmlContent) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()));
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(htmlContent, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);
    }

    // Update status bar with message
}
