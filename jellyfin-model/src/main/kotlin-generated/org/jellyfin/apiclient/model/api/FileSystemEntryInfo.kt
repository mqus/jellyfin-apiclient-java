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
 * Class FileSystemEntryInfo.
 */
@Serializable
public data class FileSystemEntryInfo(
	/**
	 * Gets the name.
	 */
	@SerialName("Name")
	public val name: String? = null,
	/**
	 * Gets the path.
	 */
	@SerialName("Path")
	public val path: String? = null,
	@SerialName("Type")
	public val type: FileSystemEntryType? = null
)
