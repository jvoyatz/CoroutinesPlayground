# Coroutines

## 1. Intro

### 1.1 What are coroutines?
1. new style of concurrency
2. simplify asynchronous code
3. when using threads, you need callbacks to get things done

### 1.2 Why use coroutines?

In Android enviroment, each app has a main thread that handles the rendering of the UI components.
In case this thread, has many work to do, the app seems to be lagging or to be slow. By using Coroutines,
we can avoid this issue, in a simple and easy way.


## Notes

* CoroutineScope creates new coroutines and handles their lifecycle
* CoroutineContext configures and changes the way coroutines behavior

* **CoroutineContext**__



# 1. Coroutines, what?

* simplify asynchronous code
* new style of concurrency

# 2. Coroutines, why?

* transfer the execution of heavy tasks out of the main thread (this applies to Android)
* gives the capability to write main safe functions

# 3. Basic stuff

* Kotlin offers suspend keyword to mark a certain method as suspendable
  * Marking a method as suspended, means that it can be paused and resumed at a later time
* It does not block the thread
* This keyword does not mean that your code will run on a background thread
  * That means, coroutines can start in the UI thread 
  * However, they don't block the thread that started them

# 4. CoroutineContext

* Collection of elements used to config the behavior of a coroutine
  * Job, lifecycle of the coroutine
  * CorourineDispatcher, defines the thread that this coroutine will be executed to
  * CoroutineExceptionHandler, manages uncaught exceptions
  * CoroutineName, changes the coroutine's name
  * `+` operator is overridden
    * That means, you can write an expression like this
      * `(Dispatchers.Main + CoroutineName("context"))`

# 5. CoroutineScope
* manages all the coroutines that it creates
* by cancelling it, you can all those coroutines
  * `scope.cancel()`
  * this action does not immediately cancel all coroutines
* Creating a scope to manage all coroutines is suggested
  * viewmodelScope, lifecycleScope - Android env
* wrapper for coroutine context

# 6. CoroutineDispatcher
* selects the thread where coroutine will be executed 
  * Dispatchers.Default, Dispatchers.IO etc

# 7. Switching Threads
* After having executed a coroutine, you can execute an inner block of this method
  in a another thread, __meaning you are switch contenxt__ by calling
  ``withContext(Dispatchers.IO) {}``

# 8. Job /  SupervisorJob
Each coroutines comes with a Job, which identifies a particular coroutine
It's states are:
- New, Active, Completing, Completed, Cancelling and Cancelled.
* SupervisorJob,
  * similar to Job with the only exception that its children can fail independently of each other.
  * an exception to a child does not affect 
  * Passing a SupervisorJob as a parameter of a coroutine builder will not have the desired effect you would’ve thought for cancellation
    * use `supervisorScope` or `CoroutineScope(SupervisorJob())`
  *  A regular Job cancels itself and propagates exceptions all the way to the top level, while SupervisorJob relies on coroutines to handle their own exceptions and doesn't cancel other coroutines

# 9. Parent - Child Hierarchy
* When a parent's job is canceled, the children's jobs are cancelled to
* Cancelling child's job, it does not affect the paren'ts job
* When the parent’s job throws an exception, the children’s jobs are canceled too;
* When a child’s job throws an exception, the parent may be canceled;
* Parent cannot complete until all its children are complete;

# 10. Coroutine bulders
* runBlocking - bridge between normal to coroutine world
  * launch - start a new coroutine, and forget
    * remember not to use try-catch to wrap a block when starting a new coroutine using launch
      not this
      ```
      try { 
         scope.launch {
           throw SomeException()    
         }
      } catch (e: SomeException) {
        //won't be executed
      }
      ```
    * but this,
     ```
     scope.launch {
           try { 
              throw SomeException()    
           } catch (e: SomeException) {
             //won't be executed
           }
      } 
      ```
  * async
    * allows you to get the returned value by calling await
    * await waits for the block to finish without blocking the thread and resumes when the deferred operation is finished, returning the result or raising the corresponding exception
    * see AsyncBlock.kt
      * The coroutine launched by async throws an exception
      * The exception is caught and “Caught exception” is printed 
      * The coroutine delegates the exception handling to its parent 
      * The parent is cancelled because it has no exception handler
    * solution is to use supervisorScope 
      * The coroutine launched by async throws an exception 
      * The exception is caught and “Caught exception” is printed 
      * The coroutine delegates the exception handling to its parent 
      * The parent is NOT cancelled because it’s a SupervisorJob, only the child is

# 11. coroutineScope & supervisorScope
* coroutineScope creates a new CoroutineScope
  * which inherits its CoroutineContext from the outer scope
  * used to execute new coroutines in a structured way inside a suspend function
    * This function is designed for parallel decomposition of work. When any child coroutine in this scope fails, this scope fails and all the rest of the children are cancelled. It’s useful when you have many async blocks that must be canceled if any of them fails.
* supervisorScope creates a new Coroutinescope as well
  * but a failure of a child does not cause the scope to fail and does not affect its other children.
  * when an exception is thrown,it is propagated up, and if you don't provide a handling block
    it is still cancelled
  * overriding CoroutineExceptionHandler or adding a try catch block is the best approach to not cancel the parent


# 12. Cancellation
* coroutines throw cancellation exception
* when you catch an exception like this, you need to rethrow it instead of swallowing it
* you can handle exceptions by using CoroutineExceptionHandler
  * doesn't work for async?
* calling cancel does not guarantee that the coroutine operation will be terminated
  * use ensureActive or check if it is active and throw a CancellationException
  * use yield() to let other coroutines run
* Using job:
  * cancels the children
  * cancel itself
  * propagate this exception to parent
* Using SupervisorJob
  * cancel itself and does not affect the other children
  * exception handler should be provided, otherwise the default one will be called

# Summary
Coroutines building blocks:
* suspend
* CoroutineContext
* CoroutineScope
* CoroutineDispatcher
* Job/Supervisor job





## Resources

__My Resources__:
* https://kotlinlang.org/docs/coroutines-guide.html#additional-references
* https://www.thedevtavern.com/blog/posts/structured-concurrency-explained/
* https://maxkim.eu/series/kotlin-coroutines
* https://victorbrandalise.com/coroutines-part-i-grasping-the-fundamentals/
* https://www.techyourchance.com/kotlin-coroutines-android-reference-guide/
* https://www.techyourchance.com/kotlin-coroutines-supervisorjob/
* https://medium.com/androiddevelopers/coroutines-first-things-first-e6187bf3bb21
* https://medium.com/androiddevelopers/cancellation-in-coroutines-aa6b90163629
* https://medium.com/androiddevelopers/exceptions-in-coroutines-ce8da1ec060c
* https://medium.com/androiddevelopers/coroutines-on-android-part-i-getting-the-background-3e0e54d20bb
* https://medium.com/androiddevelopers/coroutines-on-android-part-ii-getting-started-3bff117176dd
* https://medium.com/androiddevelopers/coroutines-on-android-part-iii-real-work-2ba8a2ec2f45
* https://medium.com/androiddevelopers/exceptions-in-coroutines-ce8da1ec060c
* https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad
* https://medium.com/androiddevelopers/the-suspend-modifier-under-the-hood-b7ce46af624f
* https://medium.com/androiddevelopers/create-an-application-coroutinescope-using-hilt-dd444e721528
* https://elizarov.medium.com/structured-concurrency-722d765aa952
* https://mbrizic.com/blog/coroutine-cancellation-exceptions/
* https://developer.android.com/kotlin/coroutines/coroutines-adv
* https://github.com/Kotlin/kotlinx.coroutines/issues/1001#issuecomment-814261687