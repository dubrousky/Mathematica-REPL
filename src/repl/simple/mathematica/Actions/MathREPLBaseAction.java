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

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import repl.simple.mathematica.MathIcons;

import java.awt.*;

/**
 * Base plugin action
 */
public abstract class MathREPLBaseAction extends AnAction {
    public static final String TOOL_WINDOW = "Mathematica REPL";
    public MathREPLBaseAction() {
        super();
        getTemplatePresentation().setIcon(MathIcons.ACTION);
    }

    public void statusBarBalloonMsg(AnActionEvent e, MessageType messageType, String htmlContent) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()));
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(htmlContent, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);
    }

    public void editorHintMsg(AnActionEvent e, MessageType messageType, String htmlContent) {
        final Editor editor = e.getData(DataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();

        Point l = editor.getContentComponent().getVisibleRect().getLocation();
        Point o = editor.logicalPositionToXY(caretModel.getLogicalPosition());
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(htmlContent, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.fromScreen(e.getData(DataKeys.CONTEXT_MENU_POINT)),
                        Balloon.Position.atRight);

    }

    public static Balloon.Position getBalloonPosition(Editor editor) {
        final int line = editor.getCaretModel().getVisualPosition().line;
        final Rectangle area = editor.getScrollingModel().getVisibleArea();
        int startLine  = area.y / editor.getLineHeight() + 1;
        return (line - startLine) * editor.getLineHeight() < 200 ? Balloon.Position.below : Balloon.Position.above;
    }
    // Update status bar with message
}
