import kotlinx.coroutines.*

private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

fun main(): Unit = runBlocking {
    val parentJob = scope.launch {
        println("Starting the parent job!")

        launch {
            while (isActive) {
                delay(10)
                println("Doing some work...")
            }
        }.invokeOnCompletion {
            println("Cancelling all work! ")
        }
    }

    parentJob.invokeOnCompletion {
        println("The parent job is cancelled!")
    }

    delay(50)

    parentJob.cancel()
    // Take note of this delay!!!
    //delay(100)
    parentJob.join() //the correct way
    //The proper way to wait for a Job’s completion is to call job.join().
    //
    //The .join() function will suspend the calling coroutine until the joined Job is fully completed.
    // Keep in mind that .join() will always continue normally,
    // regardless of how the joined Job has been completed, as long as the Job of the calling coroutine is active.

    println("Main is done!")
}