package main.kotlin

class SerialStep(vararg steps: Step) : Step {

    companion object {
        internal fun List<Step>.execute(): Boolean {
            if (this.isEmpty()) return true
            return this.first().let { step ->
                step.execute()
                        && this.subList(1, this.size).execute()
                    .also { success -> if (!success) step.undo() }
            }
        }
    }

    private val steps = steps.toList();

    override fun execute() = steps.execute()

    override fun undo() {}

}
