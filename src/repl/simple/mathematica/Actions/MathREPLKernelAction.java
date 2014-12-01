package repl.simple.mathematica.Actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 7/15/14.
 */
public abstract class MathREPLKernelAction extends MathREPLBaseAction {
    static boolean enabled = true;
    static Map<String,Boolean> Sessions= new HashMap<String,Boolean>();
    // TODO: define the update method here
}
