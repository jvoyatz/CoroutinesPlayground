import kotlinx.coroutines.*

//When the parent’s job throws an exception, the children’s jobs are canceled too

private val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val job1 = scope.launch {
        println("started parent")

        val job2 = launch {
            println("first child job")
        }

        val job3 = launch {
            println("second child job started")
            delay(3000)
            println("second child job finished")
        }

        error("intented exception")
        delay(4500)
        println("ended parent")
    }

    println("waiting")
    Thread.sleep(5000)

    println("scope isActive ${scope.isActive}")
}