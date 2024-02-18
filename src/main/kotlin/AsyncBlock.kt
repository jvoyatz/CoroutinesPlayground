import kotlinx.coroutines.*
import java.lang.Exception

val job = Job()
val sjob = SupervisorJob()
private val scope = CoroutineScope(Dispatchers.Default + sjob)


fun asyncThatThrowsWrong() = scope.launch {
    try {
        async { throw Exception() }.await()
    } catch (e: Exception) {
        println("Caught exception")
    }
}

fun asyncThatThrows() = scope.launch {
   // supervisorScope {
        try {
            async { throw Exception() }.await()
        } catch (e: Exception) {
            println("Caught exception")
        }
    //}
}

fun main() {
    runBlocking {
        asyncThatThrows().join()
        asyncThatThrows().join()
    }
}