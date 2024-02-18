import kotlinx.coroutines.*
import java.lang.Exception

private val jobScope = CoroutineScope(Job())

//this  won't work
fun main() = runBlocking {
    try {
        jobScope.launch {   throw Exception("test") }.join()
    }catch (e: Exception) {
        println("caught an exception")
    }

    Thread.sleep(2000)
    println("finished")
}


//fun main() = runBlocking {
//    jobScope.launch {
//        try {
//            throw Exception("test")
//        } catch (e: Throwable) {
//            println("caught an exception")
//        }
//    }.join()
//
//    Thread.sleep(2000)
//    println("finished")
//}