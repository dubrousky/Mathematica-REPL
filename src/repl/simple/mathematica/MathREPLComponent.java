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

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Initializes the default colors on the first run
 */
public class MathREPLComponent implements ApplicationComponent {
    public MathREPLComponent() {
    }

    public void initComponent() {
        // TODO: insert component initialization logic here

        // Default path to the MathKernel executable path
        String mathKernelPath = "";
        // Default path to the native library directory
        String nativeLibraryPath = "";
        // Default path to the JLink.jar path
        String mathLinkPath = "";
        // Initialize defaults according to the os/arch settings
        {
            if(SystemInfo.isWindows) {
                mathKernelPath = "c:\\Program Files\\Wolfram Research\\Mathematica\\10.0.1\\MathKernel";
                nativeLibraryPath = "c:\\Program Files\\Wolfram Research\\Mathematica\\10.0.1\\SystemFiles\\Links\\JLink\\JLink.jar";
                mathLinkPath = "";
            } else if(SystemInfo.isMac) {
                mathKernelPath = "/Applications/Mathematica.app/Contents/MacOS/MathKernel";
                mathLinkPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/JLink.jar";
                if( SystemInfo.is32Bit )
                    nativeLibraryPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX";
                else
                    nativeLibraryPath = "/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX-x86-64";
            } else if(SystemInfo.isLinux) {
                mathKernelPath = "/usr/local/bin/math";
                mathLinkPath = "/usr/local/Wolfram/Mathematica/10.0/SystemFiles/Links/JLink/JLink.jar";
                if( SystemInfo.is32Bit )
                    nativeLibraryPath = "/usr/local/Wolfram/Mathematica/10.0/SystemFiles/Links/JLink/SystemFiles/Libraries/Linux";
                else
                    nativeLibraryPath = "/usr/local/Wolfram/Mathematica/10.0/SystemFiles/Links/JLink/SystemFiles/Libraries/Linux-x86-64";
            }
        }

        // Default MathLink arguments
        String mathLinkArgs = "-linkmode launch -linkname \"%s\" -mathlink";
        // Default Colors
        Color textColor = new Color(0,0,0);
        Color systemColor = new Color(150,0,128);
        Color promptColor = new Color(0, 255, 0);
        Color commentColor = new Color(0,0,255);
        Color messageColor = new Color(200,100,0);
        Color backgroundColor = new Color(255,255,255);
        Color stringColor = new Color(127,127,127);
        PropertiesComponent pc = PropertiesComponent.getInstance();
        pc.getOrInit("repl.simple.mathematica.mathkernel_path", mathKernelPath);
        pc.getOrInit("repl.simple.mathematica.mathlink_path", mathLinkPath);
        pc.getOrInit("repl.simple.mathematica.native_library_path", nativeLibraryPath);
        pc.getOrInit("repl.simple.mathematica.mathlink_args",mathLinkArgs );

        pc.getOrInitInt("repl.simple.mathematica.background", backgroundColor.getRGB());
        pc.getOrInitInt("repl.simple.mathematica.text_color", textColor.getRGB());
        pc.getOrInitInt("repl.simple.mathematica.system_color", systemColor.getRGB());
        pc.getOrInitInt("repl.simple.mathematica.string_color", stringColor.getRGB());
        pc.getOrInitInt("repl.simple.mathematica.message_color", messageColor.getRGB());
        pc.getOrInitInt("repl.simple.mathematica.comment_color", commentColor.getRGB());
        pc.getOrInitInt("repl.simple.mathematica.prompt_color", promptColor.getRGB());
        pc.getOrInit("repl.simple.mathematica.syntax_highlight", "true");


    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "MathREPLComponent";
    }
}
