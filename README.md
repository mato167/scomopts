SComOpts is simple command line parser for Scala. You can use it under GNU GPL v3.
Usage is very simple, just create instance of scomopts. Arguments and add command line arguments
which you want to support by your program (method add()). Then just call parse() and programArguments()
to get List of Arguments which were entered on command line. Getting entered argument and its value
is shown in simple example example/Example1.scala.

Calling program with argumets:
program -s -h --value 100 -p /path/to/somewhere
