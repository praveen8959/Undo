package test.kotlin

import main.kotlin.SerialStep
import main.kotlin.StatefulStep
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import test.kotlin.util.SuccessfulAction
import test.kotlin.util.SuccessfulReversal
import test.kotlin.util.UnsuccessfulAction
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SerialStepTest {

    private val successfulStep get() = StatefulStep(SuccessfulAction(), SuccessfulReversal())
    private val unsuccessfulStep get() = StatefulStep(UnsuccessfulAction(), SuccessfulReversal())

    @BeforeEach
    fun setup() {
        SuccessfulReversal.reset()
        SuccessfulAction.reset()
    }

    @Test
    fun `empty serial step does nothing`() {
        assertTrue(SerialStep().execute())
    }

    @Test
    fun `serial step with two successful step`() {
        assertTrue(SerialStep(successfulStep, successfulStep).execute())
    }

    @Test
    fun `serial step with one successful and one unccessfulstep`() {
        assertEquals(0, SuccessfulReversal.totalUndoCount)
        assertEquals(0, SuccessfulAction.totalExecuteCount)
        assertFalse(SerialStep(successfulStep, unsuccessfulStep).execute())
        assertEquals(1, SuccessfulReversal.totalUndoCount)
        assertEquals(1, SuccessfulAction.totalExecuteCount)
    }

    @Test
    fun `serial step with three successful and one unccessfulstep`() {
        assertEquals(0, SuccessfulReversal.totalUndoCount)
        assertEquals(0, SuccessfulAction.totalExecuteCount)
        assertFalse(SerialStep(successfulStep, successfulStep, unsuccessfulStep, successfulStep).execute())
        assertEquals(2, SuccessfulReversal.totalUndoCount)
        assertEquals(2, SuccessfulAction.totalExecuteCount)
    }

}