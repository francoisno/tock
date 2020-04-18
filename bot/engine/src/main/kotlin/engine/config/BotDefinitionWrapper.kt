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

package ai.tock.bot.engine.config

import ai.tock.bot.admin.answer.AnswerConfigurationType.builtin
import ai.tock.bot.admin.story.StoryDefinitionConfiguration
import ai.tock.bot.admin.story.StoryDefinitionConfigurationDAO
import ai.tock.bot.definition.*
import ai.tock.bot.definition.Intent.Companion.unknown
import ai.tock.shared.injector
import ai.tock.shared.provide
import mu.KotlinLogging

/**
 *
 */
internal class BotDefinitionWrapper(val botDefinition: BotDefinition) : BotDefinition by botDefinition {

    private val logger = KotlinLogging.logger {}

    @Volatile
    private var configuredStories: Map<String, List<ConfiguredStoryDefinition>> = emptyMap()

    @Volatile
    private var allStories: List<StoryDefinition> = botDefinition.stories

    private val dao: StoryDefinitionConfigurationDAO = injector.provide()

    @Volatile
    private var storyConfRedirections: Map<String, String?> = emptyMap()

    @Volatile
    private var storyRedirections: Map<StoryDefinition, StoryDefinition> = emptyMap()

    fun updateStories(configuredStories: List<ConfiguredStoryDefinition>) {
        logger.info { "Refreshing configured stories for ${botDefinition.botId}" }
        this.configuredStories = configuredStories.filter { it.answerType != builtin }.groupBy { it.id }
        //configured stories can override built-in
        allStories = (this.configuredStories + botDefinition.stories.groupBy { it.id }).values.flatten()
        logger.info("allStories: $allStories (${allStories.size})")

        logger.info("Computing story redirections...")
        val storyConfigurations = with(botDefinition) {
            dao.getStoryDefinitionsByNamespaceAndBotId(namespace, botId).map { story -> story.storyId to story }.toMap()
        }
        storyConfRedirections = storyConfigurations.map { it.key to followStoryRedirects(storyConfigurations, it.key, null) }.toMap().also {
            it.forEach { logger.info("Story redirection: '${it.key}' -> '${it.value}'") }
//            val storyMap = it.map { allStories.find {
//                storyDef -> storyDef.id to when (it) {
//                is ConfiguredStoryDefinition -> storyDef //!it.configuration.hasOnlyDisabledFeature(applicationId)
//                else -> storyDef
//            }
        }
        storyRedirections = allStories.map {
            logger.info("Mapping story ${it.mainIntent().name}...")
            it to when (it) {
                is ConfiguredStoryDefinition ->
                    storyConfRedirections[it.configuration.storyId].let { newId -> allStories.find { story -> story is ConfiguredStoryDefinition && story.configuration.storyId == newId } }
                            ?: it
                else -> it
            }.also {
                theStory -> logger.info("Mapped to ${theStory.mainIntent().name}...")
            }
        }.toMap().also {
            it.forEach {
                    logger.info("StoryDef redirection: ${it.key.mainIntent().name} -> ${it.value.mainIntent().name}")
            }
        }
    }

    override val stories: List<StoryDefinition>
        get() = allStories

    override fun findIntent(intent: String): Intent {
        val i = super.findIntent(intent)
        return if (i == unknown) {
            val i2 = botDefinition.findIntent(intent)
            if (i2 == unknown) BotDefinition.findIntent(stories, intent) else i2
        } else i
    }

    override fun findStoryDefinition(intent: IntentAware?): StoryDefinition {
        return findStoryDefinition(intent?.wrappedIntent()?.name)
    }

    fun followStoryRedirects(stories: Map<String, StoryDefinitionConfiguration>, requestStoryId: String, switchStoryId: String?): String? {
        return if (switchStoryId == requestStoryId)
            requestStoryId
        else
            with(switchStoryId ?: requestStoryId) {
                stories.get(this)?.features?.find { it.enabled && !it.switchToStoryId.isNullOrBlank() }?.let {
                    followStoryRedirects(stories.filterKeys { it -> this != it }, requestStoryId, it.switchToStoryId)
                } ?: this
            }
    }

    override fun findStoryDefinition(intent: String?, applicationId: String?): StoryDefinition =

            (intent?.also {
                logger.info("Entering findStoryDefinition...")
//                logger.info("allStories in wrapper: $allStories (${allStories.size})")
//
//                val storyConfigurations = with(botDefinition) {
//                    dao.getStoryDefinitionsByNamespaceAndBotId(namespace, botId).map { story -> story.storyId to story }.toMap()
//                }
//                val storyRedirects = storyConfigurations.map { it.key to followStoryRedirects(storyConfigurations, it.key, null) }.toMap()
//                storyRedirects.forEach { logger.info("Story redirect: '${it.key}' -> '${it.value}'") }
//
//                // TODO : follow redirects?

        }.let { i ->
            configuredStories[i]
                ?.firstOrNull()
                //does not take if story is disabled
                ?.takeUnless { it.configuration.hasOnlyDisabledFeature(applicationId) }
        }
            ?: BotDefinition.findStoryDefinition(
                stories.filter {
                    when (it) {
                        is ConfiguredStoryDefinition -> !it.configuration.hasOnlyDisabledFeature(applicationId)
                        else -> true
                    }
                },
                intent,
                unknownStory,
                keywordStory
            ).also {
                logger.info("Found story ${it.id} / ${it.mainIntent().name}")
            }
                    ).let {
                        storyRedirections[it] ?: it
                    }.also {
                        logger.info("Returning story ${it.id} / ${it.mainIntent().name}")
                    }

    override fun toString(): String {
        return "Wrapper($botDefinition)"
    }

}