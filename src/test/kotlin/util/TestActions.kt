package test.kotlin.util

import main.kotlin.Step

internal class DualAction : Step.Action, Step.Reversal {

    override fun execute() = true

    override fun undo() {}
}

internal class SuccessfulAction : Step.Action {
    companion object {
        internal var totalExecuteCount = 0
        internal fun reset() {
            totalExecuteCount = 0
        }
    }

    override fun execute() = true.also { totalExecuteCount += 1 }

}

internal class SuccessfulReversal : Step.Reversal {
    companion object {
        internal var totalUndoCount = 0
        internal fun reset() {
            totalUndoCount = 0
        }
    }

    override fun undo() {
        totalUndoCount += 1
    }

}

internal class UnsuccessfulAction : Step.Action {
    override fun execute() = false
}
