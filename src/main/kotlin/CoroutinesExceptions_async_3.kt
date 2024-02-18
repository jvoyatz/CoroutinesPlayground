import kotlinx.coroutines.*

private val scope = CoroutineScope(Job() + Dispatchers.Default)

fun main(): Unit = runBlocking {
    scope.launch {
        supervisorScope {
            println("I am the supervisor scope!")

            val deferred = async {
                delay(50)
                throw IllegalArgumentException("Async Boom!")
            }

            println("Supervisor scope done!")
        }
    }

    delay(200)
    println("Main is done!")
}