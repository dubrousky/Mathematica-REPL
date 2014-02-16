import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ide.util.PropertiesComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by alex on 2/2/14.
 */
public class MathREPLConfigureAction extends MathREPLBaseAction {
    class MathREPLConfig extends DialogWrapper {
        Object center;
        public MathREPLConfig() {
            super(false);
            super.init();
            setTitle("Mathematica Paths");
        }
        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            center = new ConfigCenterPanel();
            //return center;
            return ((ConfigCenterPanel)center).getRootPanel();
        }
    }
    public MathREPLConfigureAction() {
        super();
    }
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        // get values previously stored and display them as defaults on dialog
        MathREPLConfig dialog = new MathREPLConfig ();
        dialog.setModal(true);
        dialog.show();
        if( dialog.getExitCode() != 0 )
        {
            // set values to be configured into the storage
            // use them when connecting to the kernel
            PropertiesComponent pc = PropertiesComponent.getInstance();
            pc.setValue("mathkernel_path","");
            pc.setValue("native_library_path","");
            pc.setValue("connection_type","");
        }
    }
}
