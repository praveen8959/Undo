package main.kotlin

import main.kotlin.Step.Action
import main.kotlin.Step.Reversal

class StatefulStep(private val action: Action, private val reversal: Reversal) : Step {

    private var state: State = Start()

    override fun execute() = state.execute()

    override fun undo() = state.undo()

    internal interface State : Step

    internal inner class Start : State {
        override fun execute() = action.execute().also {
            state = Executed()
        }
        override fun undo() = throw IllegalStateException()
    }

    internal inner class Executed : State {
        override fun execute() = throw IllegalStateException()
        override fun undo() = reversal.undo()
    }


}