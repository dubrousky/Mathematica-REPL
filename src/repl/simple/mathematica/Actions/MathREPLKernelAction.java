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

import java.util.HashMap;
import java.util.Map;

/**
 * Base kernel related action for keeping track
 * of running sessions.
 */
public abstract class MathREPLKernelAction extends MathREPLBaseAction {
    static boolean enabled = true;
    static Map<String,Boolean> Sessions= new HashMap<String,Boolean>();
    // TODO: define the update method here
}
