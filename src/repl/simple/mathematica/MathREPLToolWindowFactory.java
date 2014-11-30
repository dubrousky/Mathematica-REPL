package repl.simple.mathematica;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

/**
 * Creates the toolbar for placing the mathematica
 * session tabs. Available by the name 'Mathematica REPL'
 */
public class MathREPLToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        // TODO: Create button panel to control the tabs/session
        //Project currentProject = DataKeys.PROJECT.getData(actionEvent.getDataContext());
        //VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(actionEvent.getDataContext());
        //Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        //TODO: add explicit jar path to wrapper
        //MathSessionWrapper.loadImplementationClass();
        //MathSessionWrapper msw = MathSessionWrapper.create();

        //ContentManager cm = toolWindow.getContentManager();
        //Content c = cm.getFactory().createContent((JScrollPane)msw.getRootPanel(),"MathREPL",true);
        //cm.addContent(c);
        /*try {
            //TODO: Start kernel on demand
            String args = "-linkmode launch -linkname \"/Applications/Mathematica.app/Contents/MacOS/MathKernel\" -mathlink";

            msw.call("setLinkArguments",args);
            msw.call("connect");
            //msw.call("setSyntaxColoring",(boolean)true);
            //msw.call("setShowTiming",(boolean)false);
            // TODO: set start action to false
            MathREPLStartKernelAction.setEnabled(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        */
    }

}
