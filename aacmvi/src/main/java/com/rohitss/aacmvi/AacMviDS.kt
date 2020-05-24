/*
 * Copyright 2020 Rohit Surwase
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rohitss.aacmvi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Contract to be implemented by DataStore
 */
open class AacMviDS<STATE, EFFECT> {

    private val _states: MutableLiveData<STATE> = MutableLiveData()
    val stateLiveData: LiveData<STATE>
        get() = _states

    private var _state: STATE? = null
    var state: STATE
        get() = _state
            ?: throw UninitializedPropertyAccessException("\"state\" was queried before being initialized")
        set(value) {
            if (enableLogs) {
                if (enableStackTrace) {
                    val stacktrace = Thread.currentThread().stackTrace
                    Log.d(
                        TAG,
                        "setting viewState from \n${stacktrace[3]}\n${stacktrace[4]}\n${stacktrace[5]}\n: ${value.toString()}"
                    )
                } else {
                    Log.d(TAG, "setting viewState : ${value.toString()}")
                }
            }
            _state = value
            _states.value = value
        }


    private val _effects: SingleLiveEvent<EFFECT> = SingleLiveEvent()
    val effectLiveData: SingleLiveEvent<EFFECT>
        get() = _effects
    var effect: EFFECT? = null
        set(value) {
            if (enableLogs) {
                if (enableStackTrace) {
                    val stacktrace = Thread.currentThread().stackTrace
                    Log.d(
                        TAG,
                        "setting viewEffect from \n${stacktrace[3]}\n${stacktrace[4]}\n${stacktrace[5]}\n: ${value.toString()}"
                    )
                } else {
                    Log.d(TAG, "setting viewEffect : ${value.toString()}")
                }
            }
            field = value
            _effects.value = value
        }

    fun onCleared() {
        _state = null
    }
}