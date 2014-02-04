/**
 * Created by alex on 2/2/14.
 */
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MathFileType extends LanguageFileType {
    public static final MathFileType INSTANCE = new MathFileType();

    private MathFileType() {
        super(MathLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Mathematica file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Mathematica language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "m";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return MathIcons.FILE;
    }
}