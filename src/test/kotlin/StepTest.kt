package test.kotlin

import main.kotlin.StatefulStep
import main.kotlin.Step.Action
import main.kotlin.Step.Reversal
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class StepTest {

    @Test
    fun `execute succeeds`() {
        assertTrue(StatefulStep(SuccessfulAction(), SuccessfulReversal()).execute())
    }

    @Test
    fun `execute succeeds only once`() {
        StatefulStep(SuccessfulAction(), SuccessfulReversal()).also { step ->
            assertTrue(step.execute())
            assertThrows<IllegalStateException> { step.execute() }
        }
    }

    @Test
    fun `execute undo`() {
        StatefulStep(SuccessfulAction(), SuccessfulReversal()).also { step ->
            assertTrue(step.execute())
            step.undo()
            assertThrows<IllegalStateException>{step.execute()}
            assertThrows<IllegalStateException>{step.undo()}
        }
    }

    @Test
    fun `execute fails`() {
        assertFalse(StatefulStep(UnsuccessfulAction(), SuccessfulReversal()).execute())
    }

    @Test
    fun `Undo fails`() {
        assertThrows<IllegalStateException> { StatefulStep(SuccessfulAction(), SuccessfulReversal()).undo() }
    }


    internal class SuccessfulAction : Action {
        override fun execute() = true
    }

    internal class SuccessfulReversal : Reversal {
        override fun undo() {}
    }

    internal class UnsuccessfulAction : Action {
        override fun execute() = false
    }


}

