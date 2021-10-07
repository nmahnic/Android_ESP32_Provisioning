package com.nicomahnic.basic_esp_provisioning.api

import retrofit2.http.*

interface ApiService {

//    @Headers("Content-Type: application/json")
//    @POST("/api/PointPlusSaleIntent")
//    suspend fun postRequest(@Body req: CreatePaymentNetworkEntity): CreatePaymentResponseNetworkEntity
//
//    @HTTP(method = "DELETE", path = "/api/PointPlusSaleIntent", hasBody = true)
//    suspend fun deleteRequest(@Body req: DeletePaymentNetworkEntity): CancelPaymentResponseNetworkEntity

//    @GET("/api/PointPlusSaleIntent?")
//    suspend fun getRequest(
//            @Query("ExternalReferenceId") externalReferenceId : Int,
//            @Query("AndroidID") androidID: String,
//            @Query("RegistrationId") registrationId: String
//    ): StatusPaymentResponseNetworkEntity

    @GET("/wifi")
    suspend fun getNetworksReq(): GetNetworksResponseNetworkEntity

    @GET("/wifisave?")
    suspend fun setCredentialsReq(
            @Query("s") ssid : String,
            @Query("p") passwd: String,
    ): SetCredentialsResponseNetworkEntity

}