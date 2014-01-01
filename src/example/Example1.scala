/*  scomopts: simple command line's arguments for Scala.
    Copyright (C) 2013 Martin Mancuska <martin@borg.sk>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses */

package example

/**
 * Created by mato on 1.1.2014.
 */



import scomopts._

/* this is simple example for using of scomopts for parsing command line */
object Example1 extends App {
  override def main(args: Array[String]) = {
    val progArgs = new Arguments(args.mkString((" ")))
    progArgs.add("-h", "--help", false, "shows some help message.")
    progArgs.add("-v", "--verbose", false, "turns on verbose mode.")
    progArgs.add("-i", "--integer", true, "some argument for integer values.")
    progArgs.add("-s", "--string", true, "string value of argument.")

  //  progArgs.printHelp

    progArgs.parse()

    val myArgs = progArgs.programArguments()
    myArgs foreach (myArg => if (myArg.getShortArg.compareTo("-v") == 0)
                                println("Set some flag for verbose mode")
                             else if (myArg.getShortArg.compareTo("-s") == 0)
                                println("Value of " + myArg.getLongArg + " is " + myArg.getArgValue)
                             else if (myArg.getLongArg.compareTo("--help") == 0)
                                progArgs.printHelp
                             else if (myArg.getShortArg.compareTo("-i") == 0)
                                println("Value of " + myArg.getShortArg + " is " + myArg.getArgValue))

  }
}
