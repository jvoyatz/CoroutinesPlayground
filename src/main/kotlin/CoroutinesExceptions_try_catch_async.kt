import kotlinx.coroutines.*

private val scope = CoroutineScope(Job() + Dispatchers.Default)

fun main(): Unit = runBlocking {
    supervisorScope {
        val deferred = async {
            delay(50)
            throw IllegalArgumentException("An utter collapse!")
        }

        try {
            deferred.await()
        } catch (e: Exception) {
            println("Supervisor has recovered from: ${e.message}")
        }

        println("Supervisor scope is done!")
    }

    delay(100)
    println("Main is done!")
}