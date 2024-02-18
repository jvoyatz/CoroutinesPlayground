import kotlinx.coroutines.*

private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

fun main() {
    runBlocking {
        val mainJob = scope.launch {
            println("Starting the main job!")
            // this is an independent coroutine,
            // not a child coroutine
            scope.launch {
                while (isActive) {
                    delay(100)
                    println("I'm alive!!!")
                }
            }
        }
        mainJob.invokeOnCompletion {
            println("The main job is completed/cancelled!")
        }

        delay(100)

        mainJob.cancel()

        delay(500)
        println("Finishing main()...")
    }
}