package com.nicomahnic.basic_esp_provisioning.core

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<STATE, EFFECT, EVENT> : ViewModel(), ViewModelContract<EVENT> {

    private val _mutableViewState: MutableLiveData<STATE> = MutableLiveData()
    fun getViewState(): LiveData<STATE> = _mutableViewState

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState
                ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewState : $value")
            _viewState = value
            _mutableViewState.value = value
        }

    private val _mutableViewEffect: SingleLiveEvent<EFFECT> = SingleLiveEvent()
    fun getViewEffect(): SingleLiveEvent<EFFECT> = _mutableViewEffect

    private var _viewEffect: EFFECT? = null
    protected var viewEffect: EFFECT
        get() = _viewEffect
                ?: throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being initialized")
        set(value) {
            Log.d(TAG, "setting viewEffect : $value")
            _viewEffect = value
            _mutableViewEffect.value = value
        }

    @CallSuper
    override fun process(viewEvent: EVENT) {
        if (!_mutableViewState.hasObservers()) {
            throw NoObserverAttachedException("mutableViewState has no observers attached! In case of custom View \"startObserving()\" function needs to be called manually")
        }

        Log.d(TAG, "processing viewEvent: $viewEvent")
    }
}

internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}

class NoObserverAttachedException(message: String) : Exception(message)