/**
 * Created by alex on 2/2/14.
 */
import com.intellij.execution.console.LanguageConsoleImpl;
import com.intellij.execution.process.ConsoleHistoryModel;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;

/**
 * @author ilyas
 */
public class MathREPLConsole extends LanguageConsoleImpl {
    public static final Key<Boolean> MATH_CONSOLE_EDITOR = Key.create("MATH_CONSOLE_EDITOR");

    private final ConsoleHistoryModel myHistoryModel;
    private final String myNReplHost;
    private final String myNReplPort;
    private MathConsoleExecuteActionHandler myExecuteHandler;

    public MathREPLConsole(Project project,
                          String title,
                          ConsoleHistoryModel historyModel,
                          String nReplHost,
                          String nReplPort) {

        super(project, title, MathLanguage.INSTANCE);
        myHistoryModel = historyModel;
        myNReplHost = nReplHost;
        myNReplPort = nReplPort;
        getConsoleEditor().getDocument().putUserData(MATH_CONSOLE_EDITOR, Boolean.TRUE);
    }

    public ConsoleHistoryModel getHistoryModel() {
        return myHistoryModel;
    }

    public MathConsoleExecuteActionHandler getExecuteHandler() {
        return myExecuteHandler;
    }

    public void setExecuteHandler(MathConsoleExecuteActionHandler handler) {
        this.myExecuteHandler = handler;
    }

    public String getNReplPort() {
        return myNReplPort;
    }

    public String getNReplHost() {
        return myNReplHost;
    }
}