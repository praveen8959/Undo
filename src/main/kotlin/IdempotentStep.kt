package main.kotlin

class IdempotentStep(private val action: Step.Action, private val reversal: Step.Reversal) : Step {

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
        override fun execute() = action.execute()
        override fun undo() = reversal.undo().also { state = Start() }
    }
}