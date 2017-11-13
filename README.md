# Atom Builder

## About
I've been thinking about making a physics-based atom game since working on [Askew](https://github.com/MantisInk/askew-coursedev). Senior fall, after getting a job, I finally to get off my lazy ass and figure out how apps work, and then make the game, so here's how it's going.

## dev help

### Building the project
1. download android sdk or just Android Studio
2. clone project
3. open IntelliJ or Android Studio
  * open project
  * open _build.gradle_ file
  * check all auto-import/default wrapper settings
  * if _failed to find target with hash string 'android-22'_, then File -> Invalidate Caches/Restart and try again
4. run configs
  * Application -> desktop
    * Main class: _com.atom.builder.desktop.DesktopLauncher_
    * Working Directory: _<repo_location>\AtomBuilder\android\assets_
    * Use classpath of module: desktop
  * Android App -> android
    * (hopefully is preset)

### links
https://github.com/libgdx/libgdx/wiki/Project-Setup-Gradle

https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA

https://github.com/libgdx/libgdx/wiki/A-simple-game

https://www.youtube.com/watch?v=CN13SZpApR0

## log
Nov. 12 2017
  * remake from scratch with libgdx and intellij
  * created basic shapes and electric fields with classes
Nov. 13 2017
  * created protons, electrons, neutrons which contribute to atom when collided
  * began ejection framework

