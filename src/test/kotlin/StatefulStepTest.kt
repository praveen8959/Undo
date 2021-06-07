package test.kotlin

import main.kotlin.StatefulStep
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import test.kotlin.util.DualAction
import test.kotlin.util.SuccessfulAction
import test.kotlin.util.SuccessfulReversal
import test.kotlin.util.UnsuccessfulAction

internal class StatefulStepTest {

    @Test
    fun `execute succeeds`() {
        assertTrue(StatefulStep(SuccessfulAction(), SuccessfulReversal()).execute())
    }

    @Test
    fun `execute succeeds only once`() {
        DualAction().also { action ->
            StatefulStep(action, action).also { step ->
                assertTrue(step.execute())
                assertThrows<IllegalStateException> { step.execute() }
            }
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
    fun `execute succeeds but only once`() {
        StatefulStep(SuccessfulAction(), SuccessfulReversal()).also {
            assertTrue(it.execute())
            assertThrows<IllegalStateException> { it.execute() }
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
}

