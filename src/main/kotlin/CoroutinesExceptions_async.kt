import kotlinx.coroutines.*


private val scope = CoroutineScope(Job() + Dispatchers.Default)

fun main(): Unit = runBlocking {
    val deferred = scope.async {
        delay(50)
        throw IllegalStateException("Async Boom!")
    }

    delay(100)

    println("I'm done")
}
