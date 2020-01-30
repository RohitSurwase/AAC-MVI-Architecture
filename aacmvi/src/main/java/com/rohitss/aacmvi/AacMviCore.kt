package com.rohitss.aacmvi

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/*
//NOTE: Just for reference, ViewModel would look like below:
//NOTE: If a View does not have "ViewEffect", we can pass "Any" as a type/parameter
// Use 'viewModelScope' to launch Coroutines, it is bound to lifecycle of the ViewModel

class ReminderViewActVM(
    application: Application
) : AacMviViewModel<ReminderViewState, ReminderViewEffect, ReminderViewEvent>(application) {

    init {
        //NOTE: viewState initialization is required otherwise UninitializedPropertyAccessException will be thrown.
        viewState = ReminderViewState(toolbarTitle = "Reminder")
    }

    override fun process(viewEvent: ReminderViewEvent) {
        super.process(viewEvent)
    }


//NOTE: Inside Activity/Fragment
//NOTE: If a View does not have "ViewEffect", we can pass "Any" as a type/parameter

class ReminderViewActivity :
    AacMviActivity<ReminderViewState, ReminderViewEffect, ReminderViewEvent, ReminderViewActVM>() {

    override val viewModel: ReminderViewActVM by getViewModel()

    override fun renderViewState(viewState: ReminderViewState) {
        //Process ViewState here
    }

    override fun renderViewEffect(viewEffect: ReminderViewEffect) {
        //Process ViewEffect here
    }

    //To pass any ViewEvent => reminderViewActVM.process(ReminderViewEvent.ReloadList)

*/

/**
 * Internal contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}

/**
 * Abstract implementation of custom MVI for ViewModels.
 * Extend your [AndroidViewModel] with this [AacMviViewModel]
 */
open class AacMviViewModel<STATE, EFFECT, EVENT>(application: Application) :
    AndroidViewModel(application), ViewModelContract<EVENT> {

    private val _viewStates: MutableLiveData<STATE> = MutableLiveData()
    val viewStates: LiveData<STATE>
        get() = _viewStates

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            _viewState = value
            _viewStates.value = value
        }

    private val _viewEffects: MutableLiveData<EFFECT> = MutableLiveData()
    val viewEffects: LiveData<EFFECT>
        get() = _viewEffects

    protected var viewEffect: EFFECT? = null
        set(value) {
            field = value
            _viewEffects.value = value
            _viewEffects.value = null
        }

    @CallSuper
    override fun process(viewEvent: EVENT) {
        Log.d("viewEvent", "$viewEvent")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
}

/**
 * Abstract implementation of custom MVI for Activities.
 * Extend your [AppCompatActivity] with this [AacMviActivity]
 */
abstract class AacMviActivity<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>> :
    AppCompatActivity() {

    abstract val viewModel: ViewModel

    private val viewStateObserver = androidx.lifecycle.Observer<STATE> {
        Log.d("viewState", "$it")
        renderViewState(it)
    }

    private val viewEffectObserver = androidx.lifecycle.Observer<EFFECT?> {
        Log.d("viewEffect", "$it")
        renderViewEffect(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewStates.observe(this, viewStateObserver)
        viewModel.viewEffects.observe(this, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT?)
}


/**
 * Abstract implementation of custom MVI for Custom Views.
 * Extend your Custom View with this [AacMviView]
 */
abstract class AacMviView<STATE, EFFECT, EVENT, ViewModel : AacMviViewModel<STATE, EFFECT, EVENT>> {

    abstract val viewModel: ViewModel

    private val viewStateObserver = androidx.lifecycle.Observer<STATE> {
        Log.d("viewState", "$it")
        renderViewState(it)
    }

    private val viewEffectObserver = androidx.lifecycle.Observer<EFFECT> {
        Log.d("viewEffect", "$it")
        renderViewEffect(it)
    }

    fun startObserving(lifecycleOwner: LifecycleOwner) {
        viewModel.viewStates.observe(lifecycleOwner, viewStateObserver)
        viewModel.viewEffects.observe(lifecycleOwner, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT?)
}