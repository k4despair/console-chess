# console-chess

This is simple chess game prototype with console UI.

It was developed as an argument that current technologies (like java) allow to write sufficient part of chess game (with AI and UI) in less than a work week.

## Requirements

Ensure that you have JDK and console that supports colored output from started processes. Which means that you should use Linux distribution or [Ubuntu for Windows](https://www.microsoft.com/en-us/p/ubuntu/9nblggh4msv6). In latter case don't forget to use right font i.e. NSimSun.

## bash instructions

Run this from folder with this project to build application classes:

```
javac $(find . -name "*.java")
```

Run this to start application:

```
java -cp src com.cchess.ChessLauncher
```

Use Ctrl+C twice to exit application.

## Usage instructions

You play white. Type your moves like e2-e4 and wait for move from AI.

Enjoy!