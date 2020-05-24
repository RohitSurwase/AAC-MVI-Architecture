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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

abstract class AacMviViewModelBase<STATE, EFFECT, EVENT>(application: Application) :
    AndroidViewModel(application), ViewModelContract<EVENT> {
    abstract fun viewStates(): LiveData<STATE>
    abstract fun viewEffects(): LiveData<EFFECT>
}
