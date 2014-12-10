package repl.simple.mathematica.Actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import repl.simple.mathematica.MathREPLBundle;
import repl.simple.mathematica.Ui.ConfigCenterPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Another entry point for configuration
 */
public class MathREPLConfigureAction extends MathREPLBaseAction {
    class MathREPLConfig extends DialogWrapper {
        ConfigCenterPanel center;
        public MathREPLConfig() {
            super(false);
            super.init();
            setTitle(MathREPLBundle.message("configActionTitle"));
            this.pack();
        }
        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            center = new ConfigCenterPanel();
            PropertiesComponent pc = PropertiesComponent.getInstance();
            // Set paths
            center.setMathKernelPath(pc.getValue("repl.simple.mathematica.mathkernel_path"));
            center.setMathLinkPath(pc.getValue("repl.simple.mathematica.mathlink_path"));
            center.setNativeLibPath(pc.getValue("repl.simple.mathematica.native_library_path"));
            center.setMathLinkArgs(pc.getValue("repl.simple.mathematica.mathlink_args"));
            // Set color configuration
            center.getBackground().setSelectedColor(Color.decode(pc.getValue("repl.simple.mathematica.background")));
            center.getTextColor().setSelectedColor(Color.decode(pc.getValue("repl.simple.mathematica.text_color")));
            center.getSystemColor().setSelectedColor(Color.decode(pc.getValue("repl.simple.mathematica.system_color")));
            center.getStringColor().setSelectedColor(Color.decode(pc.getValue("repl.simple.mathematica.string_color")));
            center.getMessageColor().setSelectedColor(Color.decode(pc.getValue("repl.simple.mathematica.message_color")));
            center.getCommentColor().setSelectedColor(Color.decode(pc.getValue("repl.simple.mathematica.comment_color")));
            center.getPromptColor().setSelectedColor(Color.decode(pc.getValue("repl.simple.mathematica.prompt_color")));
            return ((ConfigCenterPanel)center).getRootPanel();
        }
    }
    public MathREPLConfigureAction() {
        super();
    }

    public void actionPerformed(AnActionEvent e) {
        // get values previously stored and display them as defaults on dialog
        MathREPLConfig dialog = new MathREPLConfig ();
        dialog.setModal(true);
        dialog.show();
        if( DialogWrapper.OK_EXIT_CODE == dialog.getExitCode() )
        {
            // set values to be configured into the storage
            // use them when connecting to the kernel
            PropertiesComponent pc = PropertiesComponent.getInstance();
            pc.setValue("repl.simple.mathematica.mathkernel_path",dialog.center.getMathKernelPath());
            pc.setValue("repl.simple.mathematica.native_library_path",dialog.center.getNativeLibPath());
            pc.setValue("repl.simple.mathematica.mathlink_path",dialog.center.getMathLinkPath());
            pc.setValue("repl.simple.mathematica.mathlink_args",dialog.center.getMathLinkArgs());
            // apply color settings
            pc.setValue("repl.simple.mathematica.text_color", Integer.toString(dialog.center.getTextColor().getSelectedColor().getRGB()));
            pc.setValue("repl.simple.mathematica.background",Integer.toString(dialog.center.getBackground().getSelectedColor().getRGB()));
            pc.setValue("repl.simple.mathematica.system_color",Integer.toString(dialog.center.getSystemColor().getSelectedColor().getRGB()));
            pc.setValue("repl.simple.mathematica.string_color",Integer.toString(dialog.center.getStringColor().getSelectedColor().getRGB()));
            pc.setValue("repl.simple.mathematica.message_color",Integer.toString(dialog.center.getMessageColor().getSelectedColor().getRGB()));
            pc.setValue("repl.simple.mathematica.prompt_color",Integer.toString(dialog.center.getPromptColor().getSelectedColor().getRGB()));
            pc.setValue("repl.simple.mathematica.comment_color",Integer.toString(dialog.center.getCommentColor().getSelectedColor().getRGB()));

        }
    }
}
