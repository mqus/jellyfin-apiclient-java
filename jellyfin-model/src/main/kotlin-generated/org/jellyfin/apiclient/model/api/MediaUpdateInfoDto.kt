// !!        WARNING
// !! DO NOT EDIT THIS FILE
//
// This file is generated by the openapi-generator module and is not meant for manual changes.
// Please read the README.md file in the openapi-generator module for additional information.
package org.jellyfin.apiclient.model.api

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Media Update Info Dto.
 */
@Serializable
public data class MediaUpdateInfoDto(
	/**
	 * Gets or sets media path.
	 */
	@SerialName("Path")
	public val path: String? = null,
	/**
	 * Gets or sets media update type.
	 * Created, Modified, Deleted.
	 */
	@SerialName("UpdateType")
	public val updateType: String? = null
)
