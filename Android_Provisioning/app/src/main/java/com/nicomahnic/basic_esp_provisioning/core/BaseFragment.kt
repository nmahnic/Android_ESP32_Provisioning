package com.nicomahnic.basic_esp_provisioning.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<STATE, EFFECT, EVENT, ViewModel : BaseViewModel<STATE, EFFECT, EVENT>>
constructor(
        contentLayoutId: Int
):
        Fragment(contentLayoutId) {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Log.d(TAG, "observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        Log.d(TAG, "observed viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Registering observers
        viewModel.getViewState().observe(this, viewStateObserver)
        viewModel.getViewEffect().observe(this, viewEffectObserver)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}