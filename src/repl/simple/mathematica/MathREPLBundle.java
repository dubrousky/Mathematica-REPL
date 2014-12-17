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

import com.intellij.CommonBundle;
import com.intellij.reference.SoftReference;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.util.ResourceBundle;

/**
 * Creates a resource bundle and expose messages templates stored there.
 */
public class MathREPLBundle {
    private static Reference<ResourceBundle> ourBundle;
    @NonNls
    private static final String BUNDLE = "MathREPLMessages";

    public static String message(@NotNull @PropertyKey(
            resourceBundle = BUNDLE
    ) String key, @NotNull Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }

    private MathREPLBundle() {
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = (ResourceBundle) SoftReference.dereference(ourBundle);
        if(bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE);
            ourBundle = new java.lang.ref.SoftReference(bundle);
        }

        return bundle;
    }
}
