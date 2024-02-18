import kotlinx.coroutines.*

private val scope = CoroutineScope(Job() + Dispatchers.Default)

fun main(): Unit = runBlocking {
    val deferred = scope.async {
        delay(50)
        throw IllegalStateException("Async Boom!")
    }

    delay(100)

    // the exception will be thrown here
    deferred.await()

    println("I'm done")
}