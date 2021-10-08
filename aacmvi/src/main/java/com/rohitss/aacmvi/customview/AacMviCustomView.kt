/*
 * Copyright 2021 Rohit Surwase
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

package com.rohitss.aacmvi.customview

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.rohitss.aacmvi.common.AacMviConfig.enableLogs
import com.rohitss.aacmvi.common.AacMviViewModel
import com.rohitss.aacmvi.util.TAG

/**
 * Create Custom Views by Extending this class.
 * Do not forget to call [startObserving] function inside constructor or similar method.
 * Otherwise, it throws [com.rohitss.aacmvi.common.NoObserverAttachedException].
 *
 * Also @see [AacMviViewModel] for [STATE], [EFFECT] and [EVENT] explanation.
 * @param ViewModel: Respective ViewModel class for this View which extends [AacMviViewModel]
 *
 * @author Rohit Surwase
 * @author https://github.com/RohitSurwase
 * @version 1.0
 * @since 1.0
 */
abstract class AacMviCustomView<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>> {

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

    /**
     * Call this function to start observing viewState and viewEffect.
     * Also, make sure you call this function after all views are initialized.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun startObserving(lifecycleOwner: LifecycleOwner) {
        //Registering observers
        viewModel.viewStates().observe(lifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(lifecycleOwner, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}