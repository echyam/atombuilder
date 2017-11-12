# Atom Builder

## dev help

### Building the project
1. clone project
2. open IntelliJ or Android Studio
  * open project
  * open _build.gradle_ file
  * check all auto-import/default wrapper settings
  * if _failed to find target with hash string 'android-22'_, then File -> Invalidate Caches/Restart and try again
3. run configs
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

