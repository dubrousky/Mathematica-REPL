package repl.simple.mathematica.Ui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.ex.DefaultColorSchemesManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.io.fs.FileSystem;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import sun.awt.windows.ThemeReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * This class implements the plugins configuration panel
 * and allows to configure the paths to the MathKernel executable
 * and JLink libraries installed in the system.
 *
 * Plugin will pick up the paths to modify the the java system library path
 * to loa the JLink.jar
 */
public class MathREPLSettings implements Configurable {

    private String mailUser;

    private String mailServer;

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public String getMailServer() {
        return mailServer;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }
    //Creation of Extension to applicationConfigurable
    //To create a plugin that contributes to the applicationConfigurable extension point and customizes the IDE Settings area of the Setting dialog box, perform the following principal steps:

    //In your plugin project, create a Java class that implements the Configurable interface.
    //In this class, override the following methods:
    //createComponent: creates the UI visual form and returns its root element.
    //apply: this method is called when the user clicks the OK or Apply button.
    //reset: this method is called when the user clicks the Cancel button.
    //isModified: this method is regularly called to check the form for changes. If the method returns false, the Apply button is disabled.
    //disposeUIResources: this method is called when the user closes the form. In this method, you can, for example, release the resources used by the form.
    //In the plugin configuration file plugin.xml, create the <extensions defaultExtensionNs="com.intellij"> </extensions> section.
    //        To this section, add the <applicationConfigurable instance=%MyJavaClassName%></applicationConfigurable> section, where the %MyJavaClassName% refers to the name of your Java class implementing the Configurable interface (see Step 1).

    // Default path to the MathKernel executable path
    private String mathKernelPath;
    // Default path to the native library directory
    private String nativeLibraryPath;
    // Default path to the JLink.jar path
    private String mathLinkPath;
    // Initialize defaults according to the os/arch settings
    {
        if(SystemInfo.isWindows) {
            mathKernelPath = "c:\\Program Files\\Wolfram Research\\Mathematica\\10.0.1\\MathKernel";
            nativeLibraryPath = "c:\\Program Files\\Wolfram Research\\Mathematica\\10.0.1\\SystemFiles\\Links\\JLink\\JLink.jar";
            mathLinkPath = "";
        } else if(SystemInfo.isMac) {
            mathKernelPath = "/Applications/Mathematica.app/Contents/MacOS/MathKernel";
            mathLinkPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/JLink.jar";
            if( SystemInfo.is32Bit )
                nativeLibraryPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX";
            else
                nativeLibraryPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX-x86-64";
        } else if(SystemInfo.isLinux) {
            mathKernelPath = "/usr/local/bin/math";
            mathLinkPath = "/usr/local/Wolfram/Mathematica/10.0/SystemFiles/Links/JLink/JLink.jar";
            if( SystemInfo.is32Bit )
                nativeLibraryPath = "/usr/local/Wolfram/Mathematica/10.0/SystemFiles/Links/JLink/SystemFiles/Libraries/Linux";
            else
                nativeLibraryPath = "/usr/local/Wolfram/Mathematica/10.0/SystemFiles/Links/JLink/SystemFiles/Libraries/Linux-x86-64";
        }
    }

    // Default MathLink arguments
    private String mathLinkArgs = "-linkmode launch -linkname \"%s\" -mathlink";



    // Default Colors
    Color textColor = new Color(0,0,0);
    Color systemColor = new Color(150,0,128);
    Color promptColor = new Color(0, 255, 0);
    Color commentColor = new Color(0,0,255);
    Color messageColor = new Color(200,100,0);
    Color backgroundColor = new Color(255,255,255);
    Color stringColor = new Color(127,127,127);



    /**
     * Returns the configured path to the MathKernel executable
     * @return path to the executable
     */
    public String getMathKernelPath() {
        return mathKernelPath;
    }

    /**
     * Sets the current MathKernel path
     * @param mathKernelPath
     */
    public void setMathKernelPath(String mathKernelPath) {
        this.mathKernelPath = mathKernelPath;
    }

    public String getNativeLibPath() {
        return nativeLibraryPath;
    }

    public void setNativeLibPath(String nativeLibraryPath) {
        this.nativeLibraryPath = nativeLibraryPath;
    }

    public String getMathLinkPath() {
        return mathLinkPath;
    }

    public void setMathLinkPath(String mathLinkPath) {
        this.mathLinkPath = mathLinkPath;
    }

    public String getMathLinkArgs() {
        return mathLinkArgs;
    }

    public void setMathLinkArgs(String mathLinkArgs) {
        this.mathLinkArgs = mathLinkArgs;
    }

    // Instance of the configuration panel
    ConfigCenterPanel confInst;

    @Nls
    @Override
    public String getDisplayName() {
        return "Mathematica REPL";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return " ";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        confInst = new ConfigCenterPanel();

        PropertiesComponent pc = PropertiesComponent.getInstance();

        // Set the paths from persistence or default
        // Set math paths
        confInst.setMathKernelPath(pc.getOrInit("repl.simple.mathematica.mathkernel_path", getMathKernelPath()));
        confInst.setMathLinkPath(pc.getOrInit("repl.simple.mathematica.mathlink_path", getMathLinkPath()));
        confInst.setNativeLibPath(pc.getOrInit("repl.simple.mathematica.native_library_path", getNativeLibPath()));
        confInst.setMathLinkArgs(pc.getOrInit("repl.simple.mathematica.mathlink_args", getMathLinkArgs()));
        // Set colors
        /* TODO: Add configuration based on the current theme
        EditorColorsManager esm = EditorColorsManager.getInstance();
        EditorColorsScheme scheme = esm.getGlobalScheme();
        confInst.background.setSelectedColor(scheme.getDefaultBackground());
        */
        confInst.background.setSelectedColor(new Color(pc.getOrInitInt("repl.simple.mathematica.background",backgroundColor.getRGB())));
        confInst.textColor.setSelectedColor(new Color(pc.getOrInitInt("repl.simple.mathematica.text_color", textColor.getRGB())));
        confInst.systemColor.setSelectedColor(new Color(pc.getOrInitInt("repl.simple.mathematica.system_color", systemColor.getRGB())));
        confInst.stringColor.setSelectedColor(new Color(pc.getOrInitInt("repl.simple.mathematica.string_color", stringColor.getRGB())));
        confInst.messageColor.setSelectedColor(new Color(pc.getOrInitInt("repl.simple.mathematica.message_color",messageColor.getRGB())));
        confInst.commentColor.setSelectedColor(new Color(pc.getOrInitInt("repl.simple.mathematica.comment_color", commentColor.getRGB())));
        confInst.promptColor.setSelectedColor(new Color(pc.getOrInitInt("repl.simple.mathematica.prompt_color", promptColor.getRGB())));
        confInst.syntaxHighlight.setSelected(Boolean.parseBoolean(pc.getOrInit("repl.simple.mathematica.syntax_highlight", "true")));
        return confInst.getRootPanel();
    }

    /**
     * Checks if the content of the configuration form is changed
     * @return true if the content was modified
     */
    @Override
    public boolean isModified() {
        PropertiesComponent pc = PropertiesComponent.getInstance();
        return  pc.getValue("repl.simple.mathematica.mathkernel_path")     != confInst.getMathKernelPath()
                || pc.getValue("repl.simple.mathematica.mathlink_path")    != confInst.getMathLinkPath()
                || pc.getValue("repl.simple.mathematica.native_path")      != confInst.getNativeLibPath()
                || pc.getValue("repl.simple.mathematica.mathlink_args")    != confInst.getMathLinkArgs()
                || pc.getValue("repl.simple.mathematica.text_color")       != Integer.toString(confInst.getTextColor().getSelectedColor().getRGB())
                || pc.getValue("repl.simple.mathematica.background")       != Integer.toString(confInst.getBackground().getSelectedColor().getRGB())
                || pc.getValue("repl.simple.mathematica.comment_color")    != Integer.toString(confInst.getCommentColor().getSelectedColor().getRGB())
                || pc.getValue("repl.simple.mathematica.message_color")    != Integer.toString(confInst.getMessageColor().getSelectedColor().getRGB())
                || pc.getValue("repl.simple.mathematica.string_color")     != Integer.toString(confInst.getStringColor().getSelectedColor().getRGB())
                || pc.getValue("repl.simple.mathematica.system_color")     != Integer.toString(confInst.getSystemColor().getSelectedColor().getRGB())
                || pc.getValue("repl.simple.mathematica.prompt_color")     != Integer.toString(confInst.getPromptColor().getSelectedColor().getRGB())
                || pc.getValue("repl.simple.mathematica.syntax_highlight") != String.valueOf(confInst.syntaxHighlight.isSelected());
    }

    /**
     * Applies the current configuration of the form
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {

        // Validate paths and throw exception
        if( !new File(confInst.getMathKernelPath()).isFile()
                || !new File(confInst.getMathLinkPath()).isFile()
                || !new File(confInst.getNativeLibPath()).isDirectory()
                || confInst.getMathLinkArgs().isEmpty())
        {
            throw new ConfigurationException("Path to the JLink is incorrect");
        }

        // Set persistence from the form values
        PropertiesComponent pc = PropertiesComponent.getInstance();

        // Paths
        pc.setValue("repl.simple.mathematica.mathkernel_path",confInst.getMathKernelPath());
        pc.setValue("repl.simple.mathematica.native_library_path",confInst.getNativeLibPath());
        pc.setValue("repl.simple.mathematica.mathlink_path",confInst.getMathLinkPath());
        pc.setValue("repl.simple.mathematica.mathlink_args",confInst.getMathLinkArgs());
        // Colors
        pc.setValue("repl.simple.mathematica.text_color", Integer.toString(confInst.textColor.getSelectedColor().getRGB()));
        pc.setValue("repl.simple.mathematica.background",Integer.toString(confInst.background.getSelectedColor().getRGB()));
        pc.setValue("repl.simple.mathematica.system_color",Integer.toString(confInst.systemColor.getSelectedColor().getRGB()));
        pc.setValue("repl.simple.mathematica.string_color",Integer.toString(confInst.stringColor.getSelectedColor().getRGB()));
        pc.setValue("repl.simple.mathematica.message_color",Integer.toString(confInst.messageColor.getSelectedColor().getRGB()));
        pc.setValue("repl.simple.mathematica.prompt_color",Integer.toString(confInst.promptColor.getSelectedColor().getRGB()));
        pc.setValue("repl.simple.mathematica.comment_color",Integer.toString(confInst.commentColor.getSelectedColor().getRGB()));
        pc.setValue("repl.simple.mathematica.syntax_highlight",String.valueOf(confInst.syntaxHighlight.isSelected()));

    }

    /**
     * resets the content of the configuration form to defaults
     */
    @Override
    public void reset() {

        PropertiesComponent pc = PropertiesComponent.getInstance();
        // reset form to persistent values or defaults
        confInst.setMathKernelPath(pc.getValue("repl.simple.mathematica.mathkernel_path",getMathKernelPath()));
        confInst.setMathLinkPath(pc.getValue("repl.simple.mathematica.mathlink_path", getMathLinkPath()));
        confInst.setNativeLibPath(pc.getValue("repl.simple.mathematica.native_library_path", getNativeLibPath()));
        confInst.setMathLinkArgs(pc.getValue("repl.simple.mathematica.mathlink_args", getMathLinkArgs()));

        confInst.background.setSelectedColor(Color.decode(pc.getOrInit("repl.simple.mathematica.background", Integer.toString(backgroundColor.getRGB()))));
        confInst.textColor.setSelectedColor(Color.decode(pc.getOrInit("repl.simple.mathematica.text_color", Integer.toString(textColor.getRGB()))));
        confInst.systemColor.setSelectedColor(Color.decode(pc.getOrInit("repl.simple.mathematica.system_color", Integer.toString(systemColor.getRGB()))));
        confInst.stringColor.setSelectedColor(Color.decode(pc.getOrInit("repl.simple.mathematica.string_color", Integer.toString(stringColor.getRGB()))));
        confInst.messageColor.setSelectedColor(Color.decode(pc.getOrInit("repl.simple.mathematica.message_color", Integer.toString(messageColor.getRGB()))));
        confInst.commentColor.setSelectedColor(Color.decode(pc.getOrInit("repl.simple.mathematica.comment_color", Integer.toString(commentColor.getRGB()))));
        confInst.promptColor.setSelectedColor(Color.decode(pc.getOrInit("repl.simple.mathematica.prompt_color", Integer.toString(promptColor.getRGB()))));
        confInst.syntaxHighlight.setSelected(Boolean.parseBoolean(pc.getOrInit("repl.simple.mathematica.syntax_highlight", "true")));

    }

    /**
     * Nullifies the configuration form contents after the form was  closed
     */
    @Override
    public void disposeUIResources() {
        confInst = null;
    }

    public String getPluginVersion() { return "0.0.2"; }

}
