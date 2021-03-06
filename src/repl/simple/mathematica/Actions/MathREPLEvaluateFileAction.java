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

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import repl.simple.mathematica.MathSessionWrapper;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Allows to select file and then load into the repl.
 */
public class MathREPLEvaluateFileAction extends MathREPLKernelAction {
    public void update(AnActionEvent e)
    {
        ToolWindowManager twm = null;
        twm = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext()));

        ToolWindow tw = twm.getToolWindow(TOOL_WINDOW);

        Content c = tw.getContentManager().getSelectedContent();

        boolean enabled = null!=c&&Sessions.containsKey(c.getTabName()) ? Sessions.get(c.getTabName()) : true;

        e.getPresentation().setEnabled(!enabled);

    }
    public void actionPerformed(AnActionEvent e) {
        // Open file selection, read through the link
        final String path = FileChooser.chooseFile(new FileChooserDescriptor(true,false,false,false,false,false),
                                                    e.getProject(),
                                                    e.getProject().getBaseDir()).getPath();
        // Wait for response
        ToolWindow tw = null;

        tw = ToolWindowManager.getInstance(DataKeys.PROJECT.getData(e.getDataContext())).getToolWindow(TOOL_WINDOW);


        final MathSessionWrapper msw =  MathSessionWrapper.adopt(tw.getContentManager().getSelectedContent().getComponent());
        JScrollPane c = (JScrollPane) msw.getRootPanel();
        JTextPane j = null;
        try {
            j = (JTextPane)c.getClass().getMethod("getTextPane").invoke(c);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        if(null!=j) {
            final JTextPane jc = j;
            // On some platforms (e.g., Linux) we need to explicitly give the text pane the focus
            // so it is ready to accept keystrokes. We want to do this after the pane has finished
            // preparing itself (which happens on the Swing UI thread via invokeLater()), so we
            // need to use invokeLater() ourselves.
            ApplicationManager.getApplication().invokeLater(
                    new Runnable() {
                        public void run() {
                            jc.requestFocus();
                            try {
                                jc.getDocument().insertString(jc.getDocument().getLength(), "<<" + path + ";", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            try {
                                msw.call("evaluateInput");
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
    }
}
