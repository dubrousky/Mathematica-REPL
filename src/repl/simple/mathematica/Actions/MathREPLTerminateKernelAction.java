/*
    Mathematica REPL IntelliJ IDEA plugin
    Copyright (C) 2014  Aliaksandr Dubrouski

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package repl.simple.mathematica.Actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import repl.simple.mathematica.MathREPLBundle;
import repl.simple.mathematica.MathSessionWrapper;

import java.lang.reflect.InvocationTargetException;


/**
<<<<<<< HEAD
<<<<<<< HEAD
 * Terminates the Kernel session
=======
 * Terminates the kernel running in the active tab
>>>>>>> 8ebc6dc... Cleanup code and add rework the actions. Added storage for plugin icons.
=======
 * Terminates the kernel running in the active tab
>>>>>>> 34511724236e01d14c34e4ceb016169aa912577c
 */
public class MathREPLTerminateKernelAction extends MathREPLKernelAction {
    public MathREPLTerminateKernelAction() {
        super();
    }

    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null !=c && Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : true;

        e.getPresentation().setEnabled(!enabled);
    }

    public void actionPerformed(AnActionEvent e) {

        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));

        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);
        final MathSessionWrapper msw =  MathSessionWrapper.adopt(tw.getContentManager().getSelectedContent().getComponent());
        try {
            msw.call("closeLink");
            Sessions.put(tw.getContentManager().getSelectedContent().getTabName(),true);
            new Notification("REPL",
                    "JLink",
                    MathREPLBundle.message("kernelStopped"),
                    NotificationType.INFORMATION).notify( e.getProject() );
            // Change Toolbar appearance (name)
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }
}
