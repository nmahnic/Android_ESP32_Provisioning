package com.nicomahnic.basic_esp_provisioning.api

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {

//    override suspend fun postRequest(req: CreatePaymentNetworkEntity) : CreatePaymentResponseNetworkEntity = apiService.postRequest(req)

//    override suspend fun deleteRequest(req: DeletePaymentNetworkEntity) : CancelPaymentResponseNetworkEntity = apiService.deleteRequest(req)

    override suspend fun getNetworksReq() : GetNetworksResponseNetworkEntity = apiService.getNetworksReq()

    override suspend fun setCredentialsReq(ssid: String, passwd: String) : SetCredentialsResponseNetworkEntity = apiService.setCredentialsReq(ssid, passwd)

}