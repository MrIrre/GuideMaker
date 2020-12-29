package com.example.myguides.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AsyncRunner {
    companion object {
        fun <T> runAsync(asyncCall: suspend () -> T, asyncCallHandleFunction: suspend (T) -> Unit) {
            GlobalScope.launch(Dispatchers.Default) {
                val asyncCallResult = asyncCall()
                GlobalScope.launch(Dispatchers.Main) {
                    asyncCallHandleFunction(asyncCallResult)
                }
            }
        }
    }
}
