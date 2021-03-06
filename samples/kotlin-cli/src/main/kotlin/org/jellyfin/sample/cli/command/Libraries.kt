package org.jellyfin.sample.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import kotlinx.coroutines.runBlocking
import org.jellyfin.apiclient.Jellyfin
import org.jellyfin.apiclient.api.operations.SessionApi
import org.jellyfin.apiclient.api.operations.UserViewsApi
import org.jellyfin.sample.cli.serverOption
import org.jellyfin.sample.cli.tokenOption

class Libraries(
	private val jellyfin: Jellyfin
) : CliktCommand("List all libraries") {
	private val server by serverOption()
	private val token by tokenOption()

	override fun run(): Unit = runBlocking {
		val api = jellyfin.createApi(baseUrl = server, accessToken = token)
		val sessionApi = SessionApi(api)
		val userViewsApi = UserViewsApi(api)

		val sessionInfo = sessionApi.getSessions(deviceId = api.deviceInfo.id).content.firstOrNull()
		if (sessionInfo == null) println("Unknown session")

		val libraries by userViewsApi.getUserViews(sessionInfo!!.userId, includeHidden = false)

		if (libraries.items.isNullOrEmpty()) println("No libraries found")

		libraries.items?.forEach {
			println(it.name)
		}
	}
}
