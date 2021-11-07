package org.fpeterek.ti.derivationtrees.util

import scala.io.Source

object Util {
  implicit class UseSource(src: Source) {
    def use[T](fn: Source => T): T = {
      val result = fn(src)
      src.close()
      result
    }
  }
}
