import kotlinx.coroutines.*

private val scope = CoroutineScope(Job() + Dispatchers.Default)

fun main(): Unit = runBlocking {
    // Main Job
    scope.launch {
        // Child 1
        launch {
            //The exception will never get thrown by the parent coroutine.
            // As we have discussed before, a parent coroutine always waits for its children to complete.
            // However, because we have introduced an infinite loop inside the Child 1,
            // it will never complete and the parent will keep waiting indefinitely,
            // or in this case while the main is running.
            while (isActive) //try setting it to true
            {
                // run
            }
        }.printOnComplete("Child 1 is cancelled!")

        // Child 2
        launch {
            delay(500)
            println("Here goes boom...")
            throw IllegalArgumentException("Boom!")
        }.printOnComplete("Child 2 is cancelled!")
    }.printOnComplete("Main Job has completed!")

    // Random coroutine on the same scope
    scope.launch {
        while (isActive) {
            // run
        }
    }.printOnComplete("Random coroutine is cancelled!")

    delay(1000)
}


fun Job.printOnComplete(message: String) {
    invokeOnCompletion {
        println(message)
    }
}