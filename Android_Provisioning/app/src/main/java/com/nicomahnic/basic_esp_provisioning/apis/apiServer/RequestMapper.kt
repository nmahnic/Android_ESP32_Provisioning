package com.nicomahnic.basic_esp_provisioning.apis.apiServer

import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceRequestNetworkEntity
import com.nicomahnic.basic_esp_provisioning.domain.models.request.PostNewDeviceRequest
import com.nicomahnic.basic_esp_provisioning.utils.EntityMapper
import javax.inject.Inject

class PostNewDeviceRequestMapper @Inject constructor():
    EntityMapper<PostNewDeviceRequestNetworkEntity, PostNewDeviceRequest> {

    override fun mapFromEntity(entity: PostNewDeviceRequestNetworkEntity): PostNewDeviceRequest {
        return PostNewDeviceRequest(
            macAddress = entity.macAddress
        )
    }

    override fun mapToEntity(domainModel: PostNewDeviceRequest): PostNewDeviceRequestNetworkEntity {
        return PostNewDeviceRequestNetworkEntity(
            macAddress = domainModel.macAddress
        )
    }

}
