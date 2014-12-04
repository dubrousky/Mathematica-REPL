package repl.simple.mathematica.Actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
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
 * Creates new session tab at the toolbar.
 */
public class MathREPLNewSession extends MathREPLKernelAction {
    static int sessionId = 0;
    @Override
    // TODO: Disable action if there are more than configured sessions running
    public void actionPerformed(final AnActionEvent e) {
        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));

        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);

        final MathSessionWrapper msw = MathSessionWrapper.create();
        if( null != msw && msw.hasImplementation() )
        {
            ContentManager cm = tw.getContentManager();

            Content c = cm.getFactory().createContent((JScrollPane) msw.getRootPanel(), "MathREPL(" + sessionId + ")", true);
            c.setCloseable(true);
            c.setShouldDisposeContent(true);
            // Close link on tab close
            c.setDisposer(new Disposable() {
                @Override
                public void dispose() {
                    try {
                        msw.call("closeLink");
                        new Notification("",
                                "JLink",
                                "The connection to the Kernel was disposed.",
                                NotificationType.INFORMATION).notify( e.getProject() );
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
            sessionId += 1;
            Sessions.put(c.getTabName(), true);
        }
        // if implementation class was not loaded notify the
        else
        {
            new Notification("",
                             "JLink loading error",
                             "The plugin was unable to load the class\n"+
                                     "required for starting Mathematica sessions.\n"+
                                     "Please, configure the plugin paths.",
                             NotificationType.ERROR).notify(e.getProject());
        }
    }
}
