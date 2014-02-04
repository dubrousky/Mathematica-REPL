import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.util.IconLoader;

/**
 * Created by alex on 2/2/14.
 */
public abstract class MathREPLBaseAction extends AnAction {
    public MathREPLBaseAction() {
        super();
        getTemplatePresentation().setIcon(IconLoader.findIcon("/resources/icon-run.png"));
    }
}
