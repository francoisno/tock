/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.vsct.tock.bot.engine

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import fr.vsct.tock.bot.admin.bot.BotApplicationConfiguration
import fr.vsct.tock.bot.admin.bot.BotApplicationConfigurationDAO
import fr.vsct.tock.bot.admin.story.StoryDefinitionConfigurationDAO
import fr.vsct.tock.bot.connector.Connector
import fr.vsct.tock.bot.connector.ConnectorCallback
import fr.vsct.tock.bot.connector.ConnectorConfiguration
import fr.vsct.tock.bot.connector.ConnectorData
import fr.vsct.tock.bot.connector.ConnectorType
import fr.vsct.tock.bot.definition.BotDefinition
import fr.vsct.tock.bot.engine.TestStoryDefinition.test
import fr.vsct.tock.bot.engine.action.Action
import fr.vsct.tock.bot.engine.dialog.Dialog
import fr.vsct.tock.bot.engine.dialog.Story
import fr.vsct.tock.bot.engine.message.Message
import fr.vsct.tock.bot.engine.message.Sentence
import fr.vsct.tock.bot.engine.nlp.NlpController
import fr.vsct.tock.bot.engine.user.PlayerId
import fr.vsct.tock.bot.engine.user.PlayerType
import fr.vsct.tock.bot.engine.user.UserLock
import fr.vsct.tock.bot.engine.user.UserTimeline
import fr.vsct.tock.bot.engine.user.UserTimelineDAO
import fr.vsct.tock.nlp.api.client.NlpClient
import fr.vsct.tock.nlp.api.client.model.Entity
import fr.vsct.tock.nlp.api.client.model.EntityType
import fr.vsct.tock.nlp.api.client.model.NlpEntityValue
import fr.vsct.tock.nlp.api.client.model.NlpResult
import fr.vsct.tock.nlp.entity.StringValue
import fr.vsct.tock.shared.defaultLocale
import fr.vsct.tock.shared.injector
import fr.vsct.tock.shared.sharedTestModule
import fr.vsct.tock.shared.tockInternalInjector
import fr.vsct.tock.translator.I18nDAO
import fr.vsct.tock.translator.TranslatorEngine
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

/**
 *
 */
abstract class BotEngineTest {

    val userLock: UserLock = mockk(relaxed = true)
    val userTimelineDAO: UserTimelineDAO = mockk(relaxed = true)
    val userId = PlayerId("id")
    val botId = PlayerId("bot", PlayerType.bot)
    open val botDefinition: BotDefinition = BotDefinitionTest()
    val dialog = Dialog(setOf(userId, botId))
    val botApplicationConfiguration: BotApplicationConfiguration = mockk(relaxed = true)
    val connectorConfiguration: ConnectorConfiguration = mockk(relaxed = true)
    val story get() = Story(botDefinition.stories.first(), test.mainIntent())
    val connectorCallback: ConnectorCallback = mockk(relaxed = true)
    val connectorData = ConnectorData(connectorCallback)

    val botConfDAO: BotApplicationConfigurationDAO = mockk(relaxed = true)
    val i18nDAO: I18nDAO = mockk(relaxed = true)
    val translator: TranslatorEngine = mockk(relaxed = true)
    val storyDefinitionConfigurationDAO: StoryDefinitionConfigurationDAO = mockk(relaxed = true)

    val entityA = Entity(EntityType("a"), "a")
    val entityAValue = NlpEntityValue(0, 1, entityA, null, false)
    val entityB = Entity(EntityType("a"), "b")
    val entityBValue = NlpEntityValue(2, 3, entityB, null, false)
    val entityC = Entity(EntityType("c"), "c")
    val entityCValue = NlpEntityValue(4, 5, entityC, null, false)
    val entityWithMergeSupport = Entity(EntityType("entityWithMergeSupport"), "entityWithMergeSupport")
    val entityWithMergeSupportValue1 = NlpEntityValue(6, 7, entityWithMergeSupport, StringValue("d"), mergeSupport = true)
    val entityWithMergeSupportValue2 = NlpEntityValue(8, 9, entityWithMergeSupport, StringValue("e"), mergeSupport = true)

    val nlpResult = NlpResult(
        test.name,
        "test",
        defaultLocale,
        listOf(entityAValue, entityBValue, entityCValue, entityWithMergeSupportValue1, entityWithMergeSupportValue2),
        emptyList(),
        1.0,
        1.0,
        "a b c d e",
        emptyMap()
    )

    val nlpClient: NlpClient = mockk(relaxed = true)
    val nlp: NlpController = mockk(relaxed = true)
    val connector: Connector = mockk(relaxed = true)
    val userTimeline = UserTimeline(userId)

    var userAction = action(Sentence("ok computer"))

    val bus: BotBus by lazy {
        fillTimeline()
        TockBotBus(connectorController, userTimeline, dialog, userAction, connectorData, botDefinition)
    }

    open fun baseModule(): Kodein.Module {
        return Kodein.Module {
            import(sharedTestModule)
            bind<NlpClient>() with provider { nlpClient }
            bind<NlpController>() with provider { nlp }
            bind<UserLock>() with provider { userLock }
            bind<UserTimelineDAO>() with provider { userTimelineDAO }
            bind<I18nDAO>() with provider { i18nDAO }
            bind<TranslatorEngine>() with provider { translator }
            bind<BotApplicationConfigurationDAO>() with provider { botConfDAO }
            bind<StoryDefinitionConfigurationDAO>() with provider { storyDefinitionConfigurationDAO }
        }
    }

    @BeforeEach
    fun before() {
        tockInternalInjector = KodeinInjector()
        injector.inject(Kodein {
            import(baseModule())
        })

        every { connector.loadProfile(any(), any()) } returns null
        every { connector.connectorType } returns ConnectorType("1")
    }

    @AfterEach
    fun after() {
        tockInternalInjector = KodeinInjector()
    }

    fun action(message: Message): Action = message.toAction(userId, "applicationId", botId)

    val registeredBus: BotBus? get() = (story.definition as TestStoryDefinition).registeredBus

    internal val bot: Bot by lazy {
        fillTimeline()
        Bot(botDefinition, botApplicationConfiguration)
    }
    internal val connectorController: TockConnectorController by lazy {
        TockConnectorController(
            bot,
            connector,
            BotVerticle(false, false),
            botDefinition,
            connectorConfiguration
        )
    }

    private var timelineFilled = false

    fun fillTimeline() {
        if (!timelineFilled) {
            timelineFilled = true
            story.actions.add(userAction)
            dialog.stories.add(story)
            userTimeline.dialogs.add(dialog)
        }
    }

}