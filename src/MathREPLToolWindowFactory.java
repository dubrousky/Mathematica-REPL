import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by alex on 2/18/14.
 */
public class MathREPLToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        MathSessionWrapper.loadLibrary();
        MathSessionWrapper msw = new MathSessionWrapper();

        ContentManager cm = toolWindow.getContentManager();
        Content c = cm.getFactory().createContent((JScrollPane)msw.getRootPanel(),"MathREPL",true);
        cm.addContent(c);
        try {
            msw.call("setLinkArgumentsArray", "-linkmode launch -linkname \"/Applications/Mathematica.app/Contents/MacOS/MathKernel\" -mathlink");
            msw.call("connect");
            msw.call("setSyntaxColoring",true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
