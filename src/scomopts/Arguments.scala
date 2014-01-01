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

package scomopts

import scala.util.matching._
import java.lang.IllegalArgumentException

/**
 * Created by mato on 29.12.2013.
 */
/* clArgs: command line arguments */
class Arguments(clArgs: String) {
  private val args = clArgs
  //definedArgs: supported command line arguments
  private var definedArgs: List[Argument] = Nil
  //enteredArgs: command line args entered by user
  private var enteredArgs: List[Argument] = Nil

  /* adds arguments to the list of definedArgs (arguments which were
     entered on command line.
   */
  def add(sArg: String, lArg: String, hasVal: Boolean, desc: String) = {
    if (sArg == Nil || sArg.isEmpty)
      throw new IllegalArgumentException("Short argument is not defined.")
    if (lArg == Nil || lArg.isEmpty)
      throw new IllegalArgumentException("Long argument is not defined.")
    if (desc == Nil || desc.isEmpty)
      throw new IllegalArgumentException("Argument description is not defined.")

    val arg: Argument = new Argument(sArg, lArg, hasVal, desc)
    definedArgs = arg :: definedArgs
  }

  /* checks and handles arguments passed via command line */
  private def checkAndHandleDefinedArguments(argList: List[String]) = {
    argList foreach (arg => if (arg.startsWith("--"))
                                  checkAndHandleLongVersion(arg)
                            else
                                  checkAndHandleShortVersion(arg))
  }

  /* checks and handles longer version of argument */
  private def checkAndHandleLongVersion(arg: String) = {
    var found: Boolean = false
    definedArgs foreach (definedArg => if (definedArg.getLongArg.compareTo(arg) == 0) {
                                          found = true
                                          if (definedArg.getHasValue == true)
                                            definedArg.setArgValue(parseValue(arg))
                                          putArgument(definedArg)
                                        })
    if (found == false)
      throw new IllegalArgumentException("Invalid argument: " + arg)
  }

  /* checks and handles shorter version of argument */
  private def checkAndHandleShortVersion(arg: String) = {
    var found: Boolean = false
    definedArgs foreach (definedArg => if (definedArg.getShortArg.compareTo(arg) == 0) {
                                            found = true
                                            if (definedArg.getHasValue == true)
                                              definedArg.setArgValue(parseValue(arg))
                                            putArgument(definedArg)
    })
    if (found == false)
      throw new IllegalArgumentException("Invalid argument: " + arg)
  }

  /* puts found arg into user's entered args (list enteredArgs) */
  private def putArgument(arg: Argument) = {
    enteredArgs = arg :: enteredArgs
  }

  /* parses argument's value
   * @return: parsed argument's value */
  private def parseValue(arg: String): String = {
    val firstIndex = args.indexOf(arg)
    var lastIndex = args.indexOf(" -", firstIndex + 1)
    /* long verion of argument is last on in the chain of arguments (at the end of command line */
    if (lastIndex == -1)
      lastIndex = args.length() - 1
    val subArg = args.substring(firstIndex, lastIndex + 1)
    val argAndVal = subArg.split(" ")
    if (argAndVal.length < 2)
      throw  new IllegalArgumentException("Value for argument " + arg + " was not entered.")

    return  argAndVal(1)
  }

  /* parses command line argument's string */
  def parse() = {
    val re = """(-{1,2}\S)+(\S*)""".r
    val parsed = re findAllIn args
    val parsedList = parsed.toList

    checkAndHandleDefinedArguments(parsedList)
  }

  /* return List of Argument which were entered on command line */
  def programArguments(): List[Argument] = {
    return enteredArgs
  }

  /* prints help message */
  def printHelp = {
    definedArgs foreach((arg: Argument) => println("%s\t%s\t\t%s".format(arg.getShortArg, arg.getLongArg, arg.getDescription)))
  }
}
