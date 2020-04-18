/*
 * Copyright (C) 2017/2020 e-voyageurs technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.tock.bot.api.service

import ai.tock.bot.admin.bot.BotConfiguration
import ai.tock.bot.admin.story.StoryDefinitionConfigurationDAO
import ai.tock.bot.api.model.configuration.ClientConfiguration
import ai.tock.bot.definition.BotDefinition
import ai.tock.bot.definition.BotProvider
import ai.tock.bot.definition.BotProviderId
import ai.tock.bot.engine.BotRepository
import ai.tock.bot.engine.config.ConfiguredStoryDefinition
import ai.tock.shared.injector
import ai.tock.shared.provide
import mu.KotlinLogging

internal class BotApiDefinitionProvider(private val configuration: BotConfiguration) : BotProvider {

    private val logger = KotlinLogging.logger {}

    @Volatile
    private var lastConfiguration: ClientConfiguration? = null
    @Volatile
    private var bot: BotDefinition
    private val handler: BotApiHandler = BotApiHandler(this, configuration)

    private val dao: StoryDefinitionConfigurationDAO = injector.provide()

    private fun mergeAllStories(clientConfiguration: ClientConfiguration?) : BotDefinition {

        // Get all stories to be able to redirect (from switchToStoryId features)
        logger.info("Client stories size: ${clientConfiguration?.stories?.size}")
        logger.info("Client stories: ${clientConfiguration?.stories}")
        val remoteStories = with(configuration) {
            dao.getStoryDefinitionsByNamespaceAndBotId(namespace, botId)
                    .filter { it.configurationName == null || it.configurationName == configuration.name }
                    .map { ConfiguredStoryDefinition(it) } // RedirectedStoryDefinition ?
        }
        logger.info("Remote stories size: ${remoteStories.size}")
        logger.info("Remote stories: ${remoteStories}")

        return BotApiDefinition(configuration, clientConfiguration, handler, remoteStories)
                .also {
                    logger.info("Bot stories size: ${it.stories.size}")
                    logger.info("Bot stories: ${it.stories}")
                    it.stories.forEach { story -> logger.info("Bot story '${story.id}' is '${story.storyHandler}'") }
                }
    }

    init {
        lastConfiguration = handler.configuration()
        bot = mergeAllStories(lastConfiguration)
    }

    fun updateIfConfigurationChange(conf: ClientConfiguration) {
        logger.debug { "check conf $conf" }
        if (conf != lastConfiguration) {
            this.lastConfiguration = conf

            bot = mergeAllStories(lastConfiguration)
            configurationUpdated = true
            BotRepository.registerBuiltInStoryDefinitions(this) // TODO : register intents if necessary
            BotRepository.checkBotConfigurations()
        }
    }

    override fun botDefinition(): BotDefinition = bot

    override fun equals(other: Any?): Boolean = botProviderId == (other as? BotProvider)?.botProviderId

    override fun hashCode(): Int = botProviderId.hashCode()

    override val botProviderId: BotProviderId =
        BotProviderId(configuration.botId, configuration.namespace, configuration.name)

    @Volatile
    override var configurationUpdated: Boolean = true
}