package main.kotlin

interface Step {

    fun execute(): Boolean
    fun undo()

}
