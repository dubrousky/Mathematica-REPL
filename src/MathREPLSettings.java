import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by alex on 2/5/14.
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
    private String mathKernelPath;
    private String nativeLibraryPath;
    private String mathLinkPath;

    public String getMathKernelPath() {
        return mathKernelPath;
    }

    public void setMathKernelPath(String mathKernelPath) {
        this.mathKernelPath = mathKernelPath;
    }

    public String getNativeLibraryPath() {
        return nativeLibraryPath;
    }

    public void setNativeLibraryPath(String nativeLibraryPath) {
        this.nativeLibraryPath = nativeLibraryPath;
    }

    public String getMathLinkPath() {
        return mathLinkPath;
    }

    public void setMathLinkPath(String mathLinkPath) {
        this.mathLinkPath = mathLinkPath;
    }

    static ConfigCenterPanel confInst;

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
        // TODO: Initialize path defaults for different arch
        //String defaultPath = "math";
        // Again for conciseness we cheat and use undocumented J/Link OS-testing functions:
        //if (Utils.isWindows())
        //    defaultPath = "c:\\Program Files\\Wolfram Research\\Mathematica\\4.2\\MathKernel";
        //else if (Utils.isMacOSX())
        //    defaultPath = "/Applications/Mathematica 4.2.app/Contents/MacOS/MathKernel";
        //kernelField.setText(defaultPath);
        return confInst.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
