import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.MessageType;

/**
 * Created by alex on 2/2/14.
 */
public class MathREPLLoadFileAction extends MathREPLBaseAction {
    public MathREPLLoadFileAction() {
        super();
    }
    public void actionPerformed(AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()));

        String htmlText = "<h1>Test Baloon Messge</h1>";
        // One of the ERROR/INFO/WARNING
        com.intellij.openapi.ui.MessageType messageType = com.intellij.openapi.ui.MessageType.INFO;
        // Display baloon when action is performed
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(htmlText, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);
    }
}
