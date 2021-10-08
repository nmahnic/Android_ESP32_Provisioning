package com.nicomahnic.basic_esp_provisioning.apis.apiESP

import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceResponseNetworkEntity
import com.nicomahnic.basic_esp_provisioning.utils.EntityMapper
import com.nicomahnic.basic_esp_provisioning.domain.models.response.GetNetworksResponse
import com.nicomahnic.basic_esp_provisioning.domain.models.response.PostNewDeviceResponse
import javax.inject.Inject


class PostNewDeviceResponseMapper @Inject constructor():
    EntityMapper<PostNewDeviceResponseNetworkEntity, PostNewDeviceResponse> {

    override fun mapFromEntity(entity: PostNewDeviceResponseNetworkEntity): PostNewDeviceResponse {
        return PostNewDeviceResponse(
                responseCode = entity.responseCode,
                macAddress = entity.macAddress
        )
    }

    override fun mapToEntity(domainModel: PostNewDeviceResponse): PostNewDeviceResponseNetworkEntity {
        return PostNewDeviceResponseNetworkEntity(
                responseCode = domainModel.responseCode,
                macAddress = domainModel.macAddress
        )
    }

}