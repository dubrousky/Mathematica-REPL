/**
 * Created by alex on 2/2/14.
 */
import com.intellij.execution.console.LanguageConsoleViewImpl;
import com.intellij.execution.process.ConsoleHistoryModel;
import com.intellij.openapi.project.Project;

/**
 * @author ilyas
 */
public class MathREPLConsoleView extends LanguageConsoleViewImpl {
    public MathREPLConsoleView(Project project, String title, ConsoleHistoryModel historyModel,
                              MathConsoleExecuteActionHandler executeHandler, String nReplHost, String nReplPort) {
        super(new MathREPLConsole(project, title, historyModel, nReplHost, nReplPort));
    }

    @Override
    public MathREPLConsole getConsole() {
        return ((MathREPLConsole) super.getConsole());
    }
}
