package repl.simple.mathematica.Sdk;

import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.util.SystemInfo;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import repl.simple.mathematica.MathIcons;
import repl.simple.mathematica.MathREPLBundle;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MathREPLSdkType extends SdkType {
    public MathREPLSdkType() {
        super(MathREPLBundle.message("sdkName"));
    }

    @NotNull
    public static MathREPLSdkType getInstance() {
        return SdkType.findInstance(MathREPLSdkType.class);
    }

    @Nullable
    @Override
    public String suggestHomePath() {
        if(SystemInfo.isWindows) {
            return "C:\\Program Files\\Wolfram Research\\Mathematica";
        } else if(SystemInfo.isMac) {
            return  "/Applications/Mathematica.app/Contents";
        } else if(SystemInfo.isLinux) {
            return "/usr/local/Wolfram/Mathematica";
        }
        return null;
    }

    @Override
    public boolean isValidSdkHome(String path) {
        return (new File(path + File.separatorChar + ".VersionID")).exists();
    }

    @Nullable
    @Override
    public String getVersionString(String sdkHome) {
        File versionID = new File(sdkHome + File.separatorChar + ".VersionID");
        String versionString = "Unknown";

        try {
            if (versionID.exists()) {
                Scanner scanner = new Scanner(versionID).useDelimiter("\\A");
                if (scanner.hasNext()) versionString = scanner.next().trim();
            }
        } catch (FileNotFoundException ignored) {
        }
        return versionString;
    }

    @Override
    public String suggestSdkName(String s, String sdkHome) {
        return "Math REPL" + getVersionString(sdkHome);
    }

    @Nullable
    @Override
    public AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel, @NotNull SdkModificator sdkModificator) {
        return null;
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return MathREPLBundle.message("sdkName");
    }

    @Override
    public void saveAdditionalData(@NotNull SdkAdditionalData sdkAdditionalData, @NotNull Element element) {

    }

    @Override
    public Icon getIcon() {
        return MathIcons.FILE;
    }

    @NotNull
    @Override
    public Icon getIconForAddAction() {
        return MathIcons.FILE;
    }

    @Override
    public boolean setupSdkPaths(@NotNull Sdk sdk, @NotNull SdkModel sdkModel) {
        final SdkModificator sdkModificator = sdk.getSdkModificator();
        final String homePath = sdk.getHomePath();
        sdkModificator.setVersionString(getVersionString(homePath));
        sdkModificator.commitChanges();
        return true;
    }
}
