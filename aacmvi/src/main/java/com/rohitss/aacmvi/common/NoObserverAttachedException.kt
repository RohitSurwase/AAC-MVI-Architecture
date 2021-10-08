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

package com.rohitss.aacmvi.common

/**
 * Custom Exception and it does what it's name suggests.
 * Thrown if you have not attached any observer to the LiveData in case
 * of [com.rohitss.aacmvi.customview.AacMviCustomView].
 */
class NoObserverAttachedException(message: String) : Exception(message)