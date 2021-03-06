// !!        WARNING
// !! DO NOT EDIT THIS FILE
//
// This file is generated by the openapi-generator module and is not meant for manual changes.
// Please read the README.md file in the openapi-generator module for additional information.
@file:UseSerializers(LocalDateTimeSerializer::class)

package org.jellyfin.apiclient.model.api

import java.time.LocalDateTime
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.jellyfin.apiclient.model.serializer.LocalDateTimeSerializer

/**
 * Class TaskExecutionInfo.
 */
@Serializable
public data class TaskResult(
	/**
	 * Gets or sets the start time UTC.
	 */
	@SerialName("StartTimeUtc")
	public val startTimeUtc: LocalDateTime,
	/**
	 * Gets or sets the end time UTC.
	 */
	@SerialName("EndTimeUtc")
	public val endTimeUtc: LocalDateTime,
	@SerialName("Status")
	public val status: TaskCompletionStatus? = null,
	/**
	 * Gets or sets the name.
	 */
	@SerialName("Name")
	public val name: String? = null,
	/**
	 * Gets or sets the key.
	 */
	@SerialName("Key")
	public val key: String? = null,
	/**
	 * Gets or sets the id.
	 */
	@SerialName("Id")
	public val id: String? = null,
	/**
	 * Gets or sets the error message.
	 */
	@SerialName("ErrorMessage")
	public val errorMessage: String? = null,
	/**
	 * Gets or sets the long error message.
	 */
	@SerialName("LongErrorMessage")
	public val longErrorMessage: String? = null
)
