package repl.simple.mathematica.Ui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * This class implements the plugins configuration panel
 * and allows to configure the paths to the MathKernel executable
 * and JLink libraries installed in the system.
 *
 * Plugin will pick up the paths to modify the the java system library path
 * to loa the JLink.jar
 */
public class MathREPLSettings implements Configurable {
    //Creation of Extension to applicationConfigurable
    //To create a plugin that contributes to the applicationConfigurable extension point and customizes the IDE Settings area of the Setting dialog box, perform the following principal steps:

    //In your plugin project, create a Java class that implements the Configurable interface.
    //In this class, override the following methods:
    //createComponent: creates the UI visual form and returns its root element.
    //apply: this method is called when the user clicks the OK or Apply button.
    //reset: this method is called when the user clicks the Cancel button.
    //isModified: this method is regularly called to check the form for changes. If the method returns false, the Apply button is disabled.
    //        disposeUIResources: this method is called when the user closes the form. In this method, you can, for example, release the resources used by the form.
    //In the plugin configuration file plugin.xml, create the <extensions defaultExtensionNs="com.intellij"> </extensions> section.
    //        To this section, add the <applicationConfigurable instance=%MyJavaClassName%></applicationConfigurable> section, where the %MyJavaClassName% refers to the name of your Java class implementing the Configurable interface (see Step 1).
    // Path to the MathKernel executable path
    private String mathKernelPath;
    // Path to the native library directory
    private String nativeLibraryPath;
    // Path to the JLink.jar path
    private String mathLinkPath;

    // MathLink arguments
    private String mathLinkArgs;

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
        // TODO: Initialize path defaults for windows
        // Again for conciseness we cheat and use undocumented J/Link OS-testing functions:
        PropertiesComponent pc = PropertiesComponent.getInstance();

        mathLinkArgs = "-linkmode launch -linkname \"%s\" -mathlink";

        if(SystemInfo.isWindows) {
            mathKernelPath = "c:\\Program Files\\Wolfram Research\\Mathematica\\10.0.1\\MathKernel";
            nativeLibraryPath = "";
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

        confInst.setMathKernelPath(pc.getValue("repl.simple.mathematica.mathkernel_path",getMathKernelPath()));
        confInst.setMathLinkPath(pc.getValue("repl.simple.mathematica.mathlink_path",getMathLinkPath()));
        confInst.setNativeLibPath(pc.getValue("repl.simple.mathematica.native_library_path",getNativeLibPath()));
        confInst.setMathLinkArgs(pc.getValue("repl.simple.mathematica.mathlink_args",getMathLinkArgs()));
        return confInst.getRootPanel();
    }

    /**
     * Checks if the content of the configuration form is changed
     * @return true if the content was modified
     */
    @Override
    public boolean isModified() {
        return mathKernelPath != confInst.getMathKernelPath() || mathLinkPath != confInst.getMathLinkPath() || nativeLibraryPath != confInst.getNativeLibPath();
    }

    /**
     * Applies the current configuration of the form
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        // set values to be configured into the storage
        // use them when connecting to the kernel
        // TODO: validate the input and throw the exception in the case the config is invalid
        // TODO: verify whether the paths exist in the system
        PropertiesComponent pc = PropertiesComponent.getInstance();
        pc.setValue("repl.simple.mathematica.mathkernel_path",confInst.getMathKernelPath());
        pc.setValue("repl.simple.mathematica.native_library_path",confInst.getNativeLibPath());
        pc.setValue("repl.simple.mathematica.mathlink_path",confInst.getMathLinkPath());
        pc.setValue("repl.simple.mathematica.mathlink_args",confInst.getMathLinkArgs());
    }

    /**
     * resets the content of the configuration form to defaults
     */
    @Override
    public void reset() {
        // TODO: Initialize path defaults for windows
        PropertiesComponent pc = PropertiesComponent.getInstance();
        mathLinkArgs = "-linkmode launch -linkname \"%s\" -mathlink";

        if(SystemInfo.isWindows) {
            mathKernelPath = "c:\\Program Files\\Wolfram Research\\Mathematica\\10.0.1\\MathKernel";
            nativeLibraryPath = "";
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
        pc.setValue("repl.simple.mathematica.mathkernel_path",getMathKernelPath());
        pc.setValue("repl.simple.mathematica.native_library_path",getNativeLibPath());
        pc.setValue("repl.simple.mathematica.mathlink_path",getMathLinkPath());
        pc.setValue("repl.simple.mathematica.mathlink_args",getMathLinkArgs());
    }

    /**
     * Nullifies the configuration form contents after the form was  closed
     */
    @Override
    public void disposeUIResources() {
        confInst = null;
    }
}
