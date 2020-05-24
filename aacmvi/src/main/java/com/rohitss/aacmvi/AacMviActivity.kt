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

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

/**
 * Create Activities by Extending this class.
 *
 * Also @see [AacMviViewModel] for [STATE], [EFFECT] and [EVENT] explanation.
 * @param ViewModel Respective ViewModel class for this activity which extends [AacMviViewModel]
 *
 * @author Rohit Surwase
 * @author https://github.com/RohitSurwase
 * @version 1.0
 * @since 1.0
 */
abstract class AacMviActivity<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>> :
    AppCompatActivity() {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        if (enableLogs) {
            Log.d(TAG, "observed viewState : $it")
        }
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        if (enableLogs) {
            Log.d(TAG, "observed viewEffect : $it")
        }
        renderViewEffect(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Registering observers
        viewModel.viewStates().observe(this, viewStateObserver)
        viewModel.viewEffects().observe(this, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}