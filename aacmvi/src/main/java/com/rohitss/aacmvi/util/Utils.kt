package com.rohitss.aacmvi.util

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