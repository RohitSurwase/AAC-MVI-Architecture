package com.rohitss.mvr.view

import android.app.Application
import com.rohitss.aacmvi.AacMviViewModel

class MainViewActVM(application: Application) :
    AacMviViewModel<MainViewState, MainViewEffect, MainViewEvent>(application) {

    init {
        viewState = MainViewState()
        viewEffect = TODO()
    }

    override fun process(viewEvent: MainViewEvent) {
        super.process(viewEvent)
    }
}
