package repl.simple.mathematica.Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by alex on 2/15/14.
 */
public class ConfigCenterPanel {
    JPanel rootPanel;
    JTextField mathLinkPath;
    JTextField mathKernelPath;
    JTextField nativeLibPath;

    JButton button1;
    JButton button2;
    JButton button3;
    private JTextField mathLinkArgs;

    public ConfigCenterPanel() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: show file selection dialog and update mathLinkPath
                // LocalFileSystem.getInstance().findFileByIoFile(new File("/"))
                Frame f =  new Frame();
                f.setSize(500,500);
                // get path for JLink java library
                FileDialog fd = new FileDialog(f, "Full path to JLink.jar", FileDialog.LOAD);
                fd.setDirectory("/");
                fd.setFile("*.jar");
                fd.setVisible(true);
                mathLinkPath.setText(fd.getFile());
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: show file selection dialog and update mathKernelPath
                Frame f =  new Frame();
                f.setSize(500,500);
                // get path for JLink java library
                FileDialog fd = new FileDialog(f, "Full path to JLink.jar", FileDialog.LOAD);
                fd.setDirectory("/");
                fd.setFile("*.jar");
                fd.setVisible(true);
                mathKernelPath.setText(fd.getFile());
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // TODO: show file selection dialog and update nativeLibPath
                Frame f =  new Frame();
                f.setSize(500,500);
                // get path for JLink java library
                FileDialog fd = new FileDialog(f, "Full path to JLink.jar", FileDialog.LOAD);
                fd.setDirectory("/");
                fd.setFile("*.jar");
                fd.setVisible(true);
                nativeLibPath.setText(fd.getDirectory());
            }
        });
    }

    public JPanel getRootPanel() { return rootPanel; }
    public String getMathLinkPath() { return mathLinkPath.getText(); }
    public String getMathKernelPath() { return  mathKernelPath.getText(); }
    public String getNativeLibPath() { return nativeLibPath.getText(); }
    public void setMathLinkPath(String path) { mathLinkPath.setText(path); }
    public void setMathKernelPath(String path) { mathKernelPath.setText(path); }
    public void setNativeLibPath(String path) { nativeLibPath.setText(path); }

}
