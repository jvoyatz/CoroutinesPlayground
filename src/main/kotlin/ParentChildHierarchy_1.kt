import kotlinx.coroutines.*

//When a parent’s job is canceled, the children’s jobs are canceled;

private val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val job1 = scope.launch {
        println("running for 1500")
        delay(1500)
        println("running for 1500 -AFTER")
    }
    val job2 = scope.launch {
        println("running for 500")
        delay(500)
        println("running for 500 -AFTER")
    }


    Thread.sleep(1000)
    scope.cancel()
    println("finished ${scope.isActive}")
}