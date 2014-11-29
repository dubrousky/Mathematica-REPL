package repl.simple.mathematica.Actions;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import repl.simple.mathematica.MathSessionWrapper;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by alex on 10/15/14.
 */
public class MathREPLNewSession extends MathREPLKernelAction {
    static int sessionId = 0;
    @Override
    public void actionPerformed(AnActionEvent e) {
        //Project currentProject = DataKeys.PROJECT.getData(actionEvent.getDataContext());
        //VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(actionEvent.getDataContext());
        //Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        // One of the ERROR/INFO/WARNING
        com.intellij.openapi.ui.MessageType messageType = com.intellij.openapi.ui.MessageType.INFO;

        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        //statusBarBalloonMsg(e, MessageType.INFO,twm.getActiveToolWindowId());

        ToolWindow tw = twm.getToolWindow("Mathematica REPL");
        //tw.getContentManager().getSelectedContent().putUserData(new Key<Boolean>("enabled"),true);
        final MathSessionWrapper msw = MathSessionWrapper.create();

        ContentManager cm = tw.getContentManager();
        // TODO: allow limited number of sessions only (due to the license limitations)
        // TODO: make it configurable
        // TODO: make disposable session close the connection to mathematica.
        Content c = cm.getFactory().createContent((JScrollPane)msw.getRootPanel(),"MathREPL("+sessionId+")",true);
        c.setCloseable(true);
        c.setShouldDisposeContent(true);
        // Close link on tab close
        c.setDisposer(new Disposable() {
            @Override
            public void dispose() {
                try {
                    msw.call("closeLink");
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        });
        cm.addContent(c);
        sessionId+=1;
        Sessions.put(c.getTabName(),true);
    }
}
