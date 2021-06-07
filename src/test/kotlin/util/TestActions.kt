package test.kotlin.util

import main.kotlin.Step

internal class DualAction : Step.Action, Step.Reversal {

    override fun execute() = true

    override fun undo() {}
}

internal class SuccessfulAction : Step.Action {
    override fun execute() = true

}

internal class SuccessfulReversal : Step.Reversal {
    override fun undo() {}
}

internal class UnsuccessfulAction : Step.Action {
    override fun execute() = false
}
