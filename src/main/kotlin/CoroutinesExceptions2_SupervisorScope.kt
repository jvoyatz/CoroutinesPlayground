import kotlinx.coroutines.*

private val scope = CoroutineScope(Job() + Dispatchers.Default)

fun main() = runBlocking {
    val result = supervisorScope {
        launch {
            delay(100)
            throw IllegalArgumentException("A total fiasco!")
        }

        launch {
            delay(200)
            println("Hi there!")
        }

        "Result!"
    }

    println("Got result: $result")
}
