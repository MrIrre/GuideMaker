package com.example.myguides.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AsyncRunner {
    companion object {
        fun <T>runAsync(asyncCall: () -> T, asyncCallHandleFunction: (T) -> Unit) {
            GlobalScope.launch(Dispatchers.Default) {
                val asyncCallResult = asyncCall()
                GlobalScope.launch(Dispatchers.Main) {
                    asyncCallHandleFunction(asyncCallResult)
                }
            }
        }
    }
}
