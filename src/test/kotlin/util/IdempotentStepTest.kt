package test.kotlin.util

import main.kotlin.IdempotentStep
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class IdempotentStepTest {

    @Test
    fun `execute succeeds time and time again`() {
        IdempotentStep(SuccessfulAction(), SuccessfulReversal()).also { step ->
            assertTrue(step.execute())
            assertTrue(step.execute())
        }
    }

    @Test
    fun `execute undo`() {
        IdempotentStep(SuccessfulAction(), SuccessfulReversal()).also { step ->
            assertTrue(step.execute())
            step.undo()
            assertTrue(step.execute())
            step.undo()
            assertTrue(step.execute())
        }
    }

    @Test
    fun `execute fails`() {
        assertFalse(IdempotentStep(UnsuccessfulAction(), SuccessfulReversal()).execute())
    }

    @Test
    fun `Undo fails`() {
        assertThrows<IllegalStateException> { IdempotentStep(SuccessfulAction(), SuccessfulReversal()).undo() }
    }

    @Test
    fun `Redundant undo fails`() {
        IdempotentStep(SuccessfulAction(), SuccessfulReversal()).also { step ->
            assertTrue(step.execute())
            step.undo()
            assertThrows<IllegalStateException> { step.undo() }
        }
    }
}