import kotlinx.coroutines.*


private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

fun main() = runBlocking {
    val childJob = Job()

    val mainJob = scope.launch {
        println("Starting the main job!")
        launch(childJob) {
            while (isActive) {
                delay(100)
                println("Scope is active: ${scope.isActive}")
            }
        }
    }
    mainJob.invokeOnCompletion {
        println("The main job is completed/cancelled!")
    }

    //cancelling the scope in this example will cancel both the mainJob and the childJob
    scope.cancel()

    delay(500)
    println("Finishing main()...")
}