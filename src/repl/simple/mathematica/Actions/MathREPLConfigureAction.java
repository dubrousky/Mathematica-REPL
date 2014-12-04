package repl.simple.mathematica.Actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import repl.simple.mathematica.Ui.ConfigCenterPanel;

import javax.swing.*;

/**
 * TODO: Configure action should be performed for disabled tab
 * TODO: Think about storing per tab session parameters (paths, args)
 */
public class MathREPLConfigureAction extends MathREPLBaseAction {
    class MathREPLConfig extends DialogWrapper {
        ConfigCenterPanel center;
        public MathREPLConfig() {
            super(false);
            super.init();
            setTitle("Mathematica Paths");
            this.pack();
        }
        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            center = new ConfigCenterPanel();
            PropertiesComponent pc = PropertiesComponent.getInstance();
            center.setMathKernelPath(pc.getValue("repl.simple.mathematica.mathkernel_path"));
            center.setMathLinkPath(pc.getValue("repl.simple.mathematica.mathlink_path"));
            center.setNativeLibPath(pc.getValue("repl.simple.mathematica.native_library_path"));
            center.setMathLinkArgs(pc.getValue("repl.simple.mathematica.mathlink_args"));
            // TODO: Add configuration
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
            pc.setValue("repl.simple.mathematica.mathkernel_path",dialog.center.getMathKernelPath());
            pc.setValue("repl.simple.mathematica.native_library_path",dialog.center.getNativeLibPath());
            pc.setValue("repl.simple.mathematica.mathlink_path",dialog.center.getMathLinkPath());
            pc.setValue("repl.simple.mathematica.mathlink_args",dialog.center.getMathLinkArgs());
        }
    }
}
