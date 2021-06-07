package main.kotlin

interface Step {

    fun execute(): Boolean
    fun undo()

    interface Action {
        fun execute(): Boolean
    }

    interface Reversal {
        fun undo()
    }


}
