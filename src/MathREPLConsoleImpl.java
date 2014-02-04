/**
 * Created by alex on 2/2/14.
 */

import com.intellij.execution.impl.ConsoleViewUtil;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.ide.DataManager;
import com.intellij.ide.highlighter.HighlighterFactory;
import com.intellij.ide.impl.TypeSafeDataProviderAdapter;
import com.intellij.injected.editor.EditorWindow;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actions.EditorActionUtil;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.impl.DelegateColorScheme;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.ex.FocusChangeListener;
import com.intellij.openapi.editor.ex.util.EditorUtil;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.editor.impl.DocumentMarkupModel;
import com.intellij.openapi.editor.impl.EditorFactoryImpl;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.openapi.util.*;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.PsiDocumentManagerImpl;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.impl.file.impl.FileManager;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.ui.SideBorder;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ui.AbstractLayoutManager;
import com.intellij.util.ui.UIUtil;
import com.intellij.util.ui.update.MergingUpdateQueue;
import com.intellij.util.ui.update.Update;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


import com.intellij.openapi.editor.ex.EditorEx;
import com.wolfram.jlink.*;
import java.awt.*;
import java.awt.event.*;
import com.wolfram.jlink.ui.ConsoleWindow;
import com.wolfram.jlink.ui.SyntaxTokenizer;
import com.wolfram.jlink.ui.BracketMatcher;
import com.wolfram.jlink.ui.MathSessionPane;
import sun.awt.VerticalBagLayout;

public class MathREPLConsoleImpl {
    MathSessionPane msp;


    private static final Logger LOG = Logger.getInstance("#" + MathREPLConsoleImpl.class.getName());
    private static final int SEPARATOR_THICKNESS = 1;



    public MathSessionPane getMathSessionPane() {return msp;}

    public void run(String[] argv) {
        System.setProperty("java.library.path","/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX-x86/");

        msp = new MathSessionPane();

        // Create the frame window to hold the pane.
        Frame frm = new Frame();
        frm.setSize(500, 500);
        frm.add(msp);
        frm.setVisible(true);
        frm.doLayout();

        frm.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                KernelLink ml = msp.getLink();
                // If we're quitting while the kernel is busy, it might not die when the link
                // is closed. So we force the issue by calling terminateKernel();
                if (ml != null)
                    ml.terminateKernel();
                System.exit(0);
            }
        });


        // Modify this for your setup.
        msp.setLinkArgumentsArray(new String[] {"-linkmode","-d32", "launch", "-linkname", "\"/Applications/Mathematica.app/Contents/MacOS/MathKernel\" -mathlink"});
        try {
            msp.connect();
            msp.setSyntaxColoring(true);
        } catch (MathLinkException e) {
            e.printStackTrace();
        }

    }


}
