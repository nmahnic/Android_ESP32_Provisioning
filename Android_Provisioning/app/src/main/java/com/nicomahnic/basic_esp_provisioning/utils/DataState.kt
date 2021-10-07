package com.nicomahnic.basic_esp_provisioning.utils

import java.lang.Exception

sealed class DataState<out T>{
    data class Success<out T>(val data: T): DataState<T>()
    data class Failure(val exception: Exception): DataState<Nothing>()
}
