package repl.simple.mathematica;

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
        //Project currentProject = DataKeys.PROJECT.getData(actionEvent.getDataContext());
        //VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(actionEvent.getDataContext());
        //Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        //TODO: add explicit jar path to wrapper
        MathSessionWrapper.loadLibrary();
        MathSessionWrapper msw = MathSessionWrapper.getSingleton();

        ContentManager cm = toolWindow.getContentManager();
        Content c = cm.getFactory().createContent((JScrollPane)msw.getRootPanel(),"MathREPL",true);
        cm.addContent(c);
        try {
            String args = "-linkmode launch -linkname \"/Applications/Mathematica.app/Contents/MacOS/MathKernel\" -mathlink";

            msw.call("setLinkArguments",args);
            msw.call("connect");
            //msw.call("setSyntaxColoring",(boolean)true);
            //msw.call("setShowTiming",(boolean)false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
