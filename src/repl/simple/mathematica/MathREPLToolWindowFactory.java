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
package repl.simple.mathematica;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.util.PopupUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;

/**
 * Creates the toolbar for placing the mathematica
 * session tabs. Available by the name 'Mathematica REPL'
 */
public class MathREPLToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ActionManager actionManager = ActionManager.getInstance();
        MathREPLActionGroup group = new MathREPLActionGroup();
        group.add(actionManager.getAction("MathREPL.repl.simple.mathematica.Actions.MathREPLNewSession"));
        group.add(actionManager.getAction("MathREPL.repl.simple.mathematica.Actions.MathREPLStartKernelAction"));
        group.add(actionManager.getAction("MathREPL.repl.simple.mathematica.Actions.MathREPLTerminateKernelAction"));

        ((ToolWindowEx)toolWindow).setAdditionalGearActions(group);
        /*
        ((ToolWindowEx)toolWindow).setTitleActions(
                actionManager.getAction("MathREPL.repl.simple.mathematica.Actions.MathREPLStartKernelAction"),
                actionManager.getAction("MathREPL.repl.simple.mathematica.Actions.MathREPLTerminateKernelAction"));
        */
    }
}
