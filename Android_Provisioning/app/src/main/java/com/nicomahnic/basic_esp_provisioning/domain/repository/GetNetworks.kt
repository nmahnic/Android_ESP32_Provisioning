package com.nicomahnic.basic_esp_provisioning.domain.repository

import android.util.Log
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.ApiHelper
import com.nicomahnic.basic_esp_provisioning.utils.DataState
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.GetNetworksResponseMapper
import com.nicomahnic.basic_esp_provisioning.domain.models.response.GetNetworksResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetNetworks @Inject constructor(
    private val apiHelper: ApiHelper,
    private val txnResponseMapper: GetNetworksResponseMapper,
) {

    suspend fun request(): Flow<DataState<GetNetworksResponse>> = flow {

        try{
            Log.d("NM", "GET NETWORKS REQUEST")
            val res = apiHelper.getNetworksReq()
            Log.d("NM", "Status Response  -> networks=${res.networks}")
            Log.d("NM", "Status Response  -> quality=${res.quality}")

            emit(DataState.Success(txnResponseMapper.mapFromEntity(res)))
        } catch (e: Exception){
            Log.d("NM","GET NETWORKS REQUEST -> FAILURE ${e}")
            emit(DataState.Failure(e))
        }
    }

}