import kotlinx.coroutines.*


private val jobScope = CoroutineScope(Job())
private val superScope = CoroutineScope(SupervisorJob())

private suspend fun testJob(scope: CoroutineScope) {
    scope.launch { throw error("launch1") }.join()
    scope.launch { throw error("launch2") }.join()
}

fun main() = runBlocking {
    println("-------------------------------")
    println("-------jobscope---------")
    testJob(jobScope)
    Thread.sleep(1000)
    println(jobScope.isActive)
    println("-------------------------------")
    println("-------supervisorScope---------")
    testJob(superScope)
    Thread.sleep(1000)
    println(superScope.isActive)

}