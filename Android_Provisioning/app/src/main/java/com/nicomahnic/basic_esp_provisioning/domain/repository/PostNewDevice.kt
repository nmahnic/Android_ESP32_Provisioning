package com.nicomahnic.basic_esp_provisioning.domain.repository

import android.util.Log
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.ApiHelper
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.GetNetworksResponseMapper
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.PostNewDeviceResponseMapper
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.PostNewDeviceRequestMapper
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.ServerHelper
import com.nicomahnic.basic_esp_provisioning.domain.models.request.PostNewDeviceRequest
import com.nicomahnic.basic_esp_provisioning.domain.models.response.GetNetworksResponse
import com.nicomahnic.basic_esp_provisioning.domain.models.response.PostNewDeviceResponse
import com.nicomahnic.basic_esp_provisioning.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class PostNewDevice @Inject constructor(
    private val apiHelper: ServerHelper,
    private val txnResponseMapper: PostNewDeviceResponseMapper,
    private val txnRequestMapper: PostNewDeviceRequestMapper
) {

    suspend fun request(req: PostNewDeviceRequest): Flow<DataState<PostNewDeviceResponse>> = flow {

        try {
            Log.d("NM", "GET NETWORKS REQUEST")
            val res = apiHelper.postRequest(txnRequestMapper.mapToEntity(req))
            Log.d("NM", "Status Response  -> networks=${res.macAddress}")
            Log.d("NM", "Status Response  -> quality=${res.responseCode}")

            emit(DataState.Success(txnResponseMapper.mapFromEntity(res)))
        } catch (e: Exception) {
            Log.d("NM", "GET NETWORKS REQUEST -> FAILURE ${e}")
            emit(DataState.Failure(e))
        }
    }

}