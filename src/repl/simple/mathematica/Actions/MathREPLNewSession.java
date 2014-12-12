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
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import repl.simple.mathematica.MathREPLBundle;
import repl.simple.mathematica.MathSessionWrapper;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Creates new session tab at the toolbar.
 */
public class MathREPLNewSession extends MathREPLKernelAction {
    static int sessionId = 0;
    @Override
    public void actionPerformed(final AnActionEvent e) {
        //Project currentProject = DataKeys.PROJECT.getData(actionEvent.getDataContext());
        //VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(actionEvent.getDataContext());
        //Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());
        // One of the ERROR/INFO/WARNING
        ToolWindowManager twm = null;

        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));


        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);
        final MathSessionWrapper msw = MathSessionWrapper.create();
        if( null != msw && msw.hasImplementation() ) {
            final ContentManager cm = tw.getContentManager();
            // TODO: allow limited number of sessions only (due to the license limitations)
            // TODO: make it configurable
            if (tw.getContentManager().getContentCount() < 3) {
                try {
                    // Make it configurable
                    msw.call("setShowTiming", false);
                    msw.call("setTextSize", 10);


                    PropertiesComponent pc = PropertiesComponent.getInstance();

                    msw.call("setSyntaxColoring",Boolean.parseBoolean(pc.getValue("repl.simple.mathematica.syntax_highlight")));
                    msw.call("setTextColor", Color.decode(pc.getValue("repl.simple.mathematica.text_color")));
                    msw.call("setSystemSymbolColor", Color.decode(pc.getValue("repl.simple.mathematica.system_color")));
                    msw.call("setStringColor", Color.decode(pc.getValue("repl.simple.mathematica.string_color")));
                    msw.call("setMessageColor", Color.decode(pc.getValue("repl.simple.mathematica.message_color")));
                    msw.call("setCommentColor", Color.decode(pc.getValue("repl.simple.mathematica.comment_color")));
                    msw.call("setPromptColor", Color.decode(pc.getValue("repl.simple.mathematica.prompt_color")));
                    msw.call("setBackgroundColor", Color.decode(pc.getValue("repl.simple.mathematica.background")));
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
                final Content c = cm.getFactory().createContent((JComponent) msw.getRootPanel(), "MathREPL(" + sessionId + ")", true);
                // Make disposable session close the connection to mathematica.
                c.setCloseable(true);
                c.setShouldDisposeContent(true);
                // Close link on tab close
                c.setDisposer(new Disposable() {
                    @Override
                    public void dispose() {
                        // Invoke later closing
                        ApplicationManager.getApplication().invokeLater(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            msw.call("closeLink");
                                            new Notification("REPL",
                                                    "JLink",
                                                    MathREPLBundle.message("connectionDisposed"),
                                                    NotificationType.INFORMATION).notify(e.getProject());

                                        } catch (NoSuchMethodException e1) {
                                            e1.printStackTrace();
                                        } catch (IllegalAccessException e1) {
                                            e1.printStackTrace();
                                        } catch (InvocationTargetException e1) {
                                            e1.printStackTrace();
                                        }

                                    }
                                }
                        );
                    }
                });

                // Set Session Attributes
                sessionId += 1;
                Sessions.put(c.getTabName(), true);
                cm.addContent(c);
            }
            else
            {
                new Notification("REPL",
                        "JLink",
                        MathREPLBundle.message("tooManyTabs"),
                        NotificationType.WARNING).notify(e.getProject());
            }
        }
        // if implementation class was not loaded send notification
        else
        {
            new Notification("REPL",
                             "JLink",
                             MathREPLBundle.message("loadJLinkError"),
                             NotificationType.ERROR).notify(e.getProject());
        }
    }
}
