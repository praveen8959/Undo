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
            state = Complete()
        }

        override fun undo() = throw IllegalStateException()
    }

    internal inner class Complete : State {
        override fun execute() = throw IllegalStateException()
        override fun undo() = reversal.undo().also { state = Reversed() }
    }

    internal inner class Reversed : State {
        override fun execute() = throw IllegalStateException()
        override fun undo() = throw IllegalStateException()
    }


}