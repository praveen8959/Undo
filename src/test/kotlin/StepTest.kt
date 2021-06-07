package test.kotlin

import main.kotlin.Step
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class StepTest {

    @Test fun `execute succeeds`(){
        assertTrue(SuccessfulStep().execute())
    }

    @Test fun `execute fails`(){
        assertFalse(UnsuccessfulStep().execute())
    }

    @Test fun `Undo fails`(){
        assertThrows<IllegalStateException> { SuccessfulStep().undo()  }
    }



    internal class SuccessfulStep : Step{
        override fun execute() = true
        override fun undo() = throw IllegalStateException()
    }

    internal class UnsuccessfulStep : Step{
        override fun execute() = false
        override fun undo() = throw IllegalStateException()
    }


}

