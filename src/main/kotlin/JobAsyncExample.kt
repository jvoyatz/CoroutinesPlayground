import kotlinx.coroutines.*

/// not the same behavior as when using launch

private val jobScope = CoroutineScope(Job())
private val superScope = CoroutineScope(SupervisorJob())

private suspend fun testJob(scope: CoroutineScope) {
    scope.async { error("launch1") }.await()
    scope.async { error("launch2") }.await()
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