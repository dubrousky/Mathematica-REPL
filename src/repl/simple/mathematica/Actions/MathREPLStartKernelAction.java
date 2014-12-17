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

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import repl.simple.mathematica.MathREPLBundle;
import repl.simple.mathematica.MathSessionWrapper;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

/**
 * Starts new kernel session in the tab
 */
public class MathREPLStartKernelAction extends MathREPLKernelAction {

    public MathREPLStartKernelAction() {
        super();

    }
    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));
        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null!=c&&Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : false;

        e.getPresentation().setEnabled(enabled);

    }

    public void actionPerformed(AnActionEvent e) {

        // get link status
        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));


        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);
        ContentManager cm = tw.getContentManager();
        Content c = cm.getSelectedContent();
        final MathSessionWrapper msw =  MathSessionWrapper.adopt(c.getComponent());
        if(null != msw)
        {
            PropertiesComponent pc = PropertiesComponent.getInstance();
            String args = String.format(pc.getValue("repl.simple.mathematica.mathlink_args"),
                                        pc.getValue("repl.simple.mathematica.mathkernel_path"));

            try {
                msw.call("setLinkArguments", args);
                // Set reasonable timeout to reach the kernel
                msw.call("setConnectTimeout",(int)10000);
                msw.call("connect");
                new Notification("REPL",
                        "JLink",
                        MathREPLBundle.message("connectionEstablished"),
                        NotificationType.INFORMATION).notify(e.getProject());
                Sessions.put(c.getTabName(),false);

            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }

        }

    }
}
