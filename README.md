# README #

### Mathematica REPL ###

* This repository contains a proof-of-concept of a plugin for IntellyJIdea(TM) IDE for Wolfram Mathematica(TM) development
* The idea is to have a wrapper plugin around the JLink library to be able to execute Mathematica code from IntellyJIdea (TM) IDE having the Mathematica installationin the system. JLink provides the way to interface the 3rd party programs to Mathematica and vice versa. The plugin uses reflection to avoid a compile time dependency on the JLink library. 
* Recent updates: the plugin works with the most rescent version of the Mathematica (10.0.1 at the time). The previous issue with the naitive libraries is gone. Connection to Mathematica(TM) works out of the box for Mac OS X 10.10. 

### How do I get set up? ###

* Dependencies:
  
  * Wolfram Mathematica 7, 10 (Other versions were not tested)
  
  * IntellyJIdea IDE installed ( Community Edition 13 tested)
  
  * IntellyJIdea SDK 

  * JRE to run the IDE (there are some issues with running the plugin under Mac OS X x64)

* Build [![Build Status](https://travis-ci.org/dubrousky/Mathematica-REPL.svg?branch=master)](https://travis-ci.org/dubrousky/Mathematica-REPL)

  * To build the plugin manually you need an IntellyJIdea SDK installed. The procedure is simple. You need to go Build->Prepare All Plugin Modules for Deployment. The IDE will do the rest. 
  * You can also download the archive from the repository. However it is osolete.
  * Travis CI builds plugin jar file. See the build status above.

* Getting started
  * Before the first usage of plugin you need to make sure that the requiremens are met - your system has a JRE and Mathematica installed
  * On the first run you need to specify the location of the Kernel executable, JLink package and Mathematica native libraries. The exact location depends on the system and the path you have chosen to install the JRE and Mathematica.
  * Here are typical paths for different systems
    * Mac OS X
      * JLink Location: `/Applications/Mathematica.app/SystemFiles/Links/JLink/JLink.jar`
      * Kernel Path: `/Applications/Mathematica.app/Contents/MacOS/MathKernel`
      * JNI native library: `/Applications/Mathematica.app/SystemFiles/Links/JLink/SystemFiles/Libraries/MacOSX`
    * Linux
    * Windows
  * Go to the IntellyJ IDEA->Preferences->IDE Settings->Mathematica REPL and provide the necessary paths. You are good to go.

### Usage ###
Having performed the steps mentioned above you can start using the Mathematica REPL plugin to execute the code from the IDE. There are the toolbox available.
* Go to the View->Tool Windows->Mathematica REPL
* Do Tools->Mathematica REPL->Start Kernel and wait for Mathematica prompt to appear
* The repl has very basic functionality and does not support the advanced Mathematica features like Manipulate or plot editing.
* The plugin provides limited syntax higlight support based on the JLink functionality. To get the full syntax higlighting support please pay attention to the Mathematica Language support at IntellyJ Idea repository.

The following hotkeys are available at the REPL tab:
```
  Ctrl-X    Cut
  Ctrl-C    Copy
  Ctrl-V    Paste 
  Ctrl-Z    Undo 
  Ctrl-Y    Redo 
  Ctrl-L    Copy Input From Above 
  Ctrl-B    Balance Brackets 
  Alt-.     Abort Computation 
  Alt-,     Interrupt Computation (brings up dialog)
            (These all use the Command key on the Macintosh)
```


### TODO ###
* Support of the remote kernel connection using math link
* Unit testing
 
### Disclaimer ###
The Mathematica and IntellyJ IDEA are the trademarks of their respective owners.
