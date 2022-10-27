package dev.leonzimmermann.demo.extendablespringdemo.util

inline operator fun <reified T> Array<T>.minus(exclude: Array<T>): Array<T> {
  return this.filter { !exclude.contains(it) }.toTypedArray()
}
