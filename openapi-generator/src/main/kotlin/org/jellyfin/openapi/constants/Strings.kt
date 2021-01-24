package org.jellyfin.openapi.constants

object Strings {
	/**
	 * Warning that is inserted at the top of all generated files
	 */
	val FILE_TOP_WARNING = """
		!!        WARNING
		!! DO NOT EDIT THIS FILE
		
		This file is generated by the openapi-generator module and is not meant for manual changes.
		Please read the README.md file in the openapi-generator module for additional information.
	""".trimIndent()

	/**
	 * Message of the @Deprecation warning for a member (function or property)
	 */
	const val DEPRECATED_MEMBER = "This member is deprecated and may be removed in the future"

	/**
	 * Message of the @Deprecation warning for a class (includes enums)
	 */
	const val DEPRECATED_CLASS = "This class is deprecated and may be removed in the future"

	/**
	 * The default service name for API operations
	 */
	const val DEFAULT_API_SERVICE = "Misc"

	/**
	 * The description used for the "includeCredentials" parameter in API URL functions
	 */
	const val INCLUDE_CREDENTIALS_DESCRIPTION = "Add the access token to the url to make an authenticated request."

	/**
	 * The suffix added to the name of a deprecated operation.
	 */
	const val DEPRECATED_OPERATION_SUFFIX = "Deprecated"

	/**
	 * The suffix added to the name of a URL operation. Added after [URL_OPERATION_SUFFIX].
	 */
	const val URL_OPERATION_SUFFIX = "Url"

	/**
	 * The suffix added to the name of a service containing API operations.
	 */
	const val API_SERVICE_SUFFIX = "Api"
}
