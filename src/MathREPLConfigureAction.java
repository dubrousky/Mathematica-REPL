import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ide.util.PropertiesComponent;
/**
 * Created by alex on 2/2/14.
 */
public class MathREPLConfigureAction extends MathREPLBaseAction {
    public MathREPLConfigureAction() {
        super();
    }
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        // get values previously stored and display them as defaults on dialog
        MathLinkConfigurationDialog dialog = new MathLinkConfigurationDialog();
        dialog.setModal(true);
        dialog.show();
        // set values to be configured into the storage
        // use them when connecting to the kernel
        PropertiesComponent pc = PropertiesComponent.getInstance();
        pc.setValue("mathkelrnel_path","");
        pc.setValue("native_library_path","");
        pc.setValue("connection_type","");
    }
}
