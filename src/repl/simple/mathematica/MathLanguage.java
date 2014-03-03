package repl.simple.mathematica;
/**
 * Created by alex on 2/2/14.
 */
import com.intellij.lang.Language;

public class MathLanguage extends Language {
    public static final MathLanguage INSTANCE = new MathLanguage();

    private MathLanguage() {
        super("Mathematica");
    }
}