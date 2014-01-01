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

/**
 * Created by mato on 29.12.2013.
 */
class Argument(sArg: String, lArg: String, hVal: Boolean, desc: String) {
  //short version of argument (-v)
  private val shortArg = sArg
  //long version of argument (--verbose)
  private val longArg = lArg
  //description for help
  private val description = desc
  //must have value
  private val hasValue = hVal
  //value for this argument (only if hasValue is set to true)
  private var value: String = ""

  def getShortArg: String = {
    shortArg
  }

  def getLongArg: String = {
    longArg
  }

  def getDescription: String = {
    description
  }

  def getHasValue: Boolean = {
    return hasValue
  }

  def setArgValue(inVal: String) = {
    value = inVal
  }
  def getArgValue: String = {
    value
  }
}
