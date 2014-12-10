package repl.simple.mathematica;

import com.intellij.CommonBundle;
import com.intellij.reference.SoftReference;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.util.ResourceBundle;

/**
 * Creates a resource bundle and expose messages templates stored there.
 */
public class MathREPLBundle {
    private static Reference<ResourceBundle> ourBundle;
    @NonNls
    private static final String BUNDLE = "repl.simple.mathematica.resources.MathREPLMessages";

    public static String message(@NotNull @PropertyKey(
            resourceBundle = BUNDLE
    ) String key, @NotNull Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }

    private MathREPLBundle() {
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = (ResourceBundle) SoftReference.dereference(ourBundle);
        if(bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE);
            ourBundle = new java.lang.ref.SoftReference(bundle);
        }

        return bundle;
    }
}
