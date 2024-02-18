import kotlinx.coroutines.*

//When a child’s job throws an exception, the parent may be canceled;

private val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    var job1: Job? = null
    var job2: Job? = null
    job1 = scope.launch {
        println("running for 1500")
        delay(15000)
        println("running for 1500 -AFTER")
    }
    job2 = scope.launch {
        println("running for 500")

        delay(2000)
        error("test exception")
    }

    println("waiting")
    Thread.sleep(5000)

    println("scope isActive ${scope.isActive}")
}