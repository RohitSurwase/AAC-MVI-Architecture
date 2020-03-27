package com.rohitss.aacmvi

internal val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            // first 23 chars
            if (name.length <= 23) name else name.substring(0, 23)
        } else {
            val name = javaClass.name
            // last 23 chars
            if (name.length <= 23) name else name.substring(name.length - 23, name.length)
        }
    }

/**
 * Internal Contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}

class NoObserverAttachedException(message: String) : Exception(message)