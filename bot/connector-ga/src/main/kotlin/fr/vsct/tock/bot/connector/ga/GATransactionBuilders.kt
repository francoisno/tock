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

package fr.vsct.tock.bot.connector.ga

import fr.vsct.tock.bot.connector.ConnectorMessage
import fr.vsct.tock.bot.connector.ga.model.GAIntent
import fr.vsct.tock.bot.connector.ga.model.request.GATransactionDecisionValue
import fr.vsct.tock.bot.connector.ga.model.request.GATransactionRequirementsCheckResult
import fr.vsct.tock.bot.connector.ga.model.response.GAAction
import fr.vsct.tock.bot.connector.ga.model.response.GAActionType
import fr.vsct.tock.bot.connector.ga.model.response.GAButton
import fr.vsct.tock.bot.connector.ga.model.response.GACart
import fr.vsct.tock.bot.connector.ga.model.response.GACustomerInfoOptions
import fr.vsct.tock.bot.connector.ga.model.response.GACustomerInfoProperty
import fr.vsct.tock.bot.connector.ga.model.response.GAExpectedIntent
import fr.vsct.tock.bot.connector.ga.model.response.GAImage
import fr.vsct.tock.bot.connector.ga.model.response.GALineItem
import fr.vsct.tock.bot.connector.ga.model.response.GALineItemType
import fr.vsct.tock.bot.connector.ga.model.response.GALineItemUpdate
import fr.vsct.tock.bot.connector.ga.model.response.GAMerchant
import fr.vsct.tock.bot.connector.ga.model.response.GAMoney
import fr.vsct.tock.bot.connector.ga.model.response.GAOrderOptions
import fr.vsct.tock.bot.connector.ga.model.response.GAOrderState
import fr.vsct.tock.bot.connector.ga.model.response.GAOrderUpdate
import fr.vsct.tock.bot.connector.ga.model.response.GAPaymentOptions
import fr.vsct.tock.bot.connector.ga.model.response.GAPrice
import fr.vsct.tock.bot.connector.ga.model.response.GAPriceType
import fr.vsct.tock.bot.connector.ga.model.response.GAProposedOrder
import fr.vsct.tock.bot.connector.ga.model.response.GAReceipt
import fr.vsct.tock.bot.connector.ga.model.response.GAState
import fr.vsct.tock.bot.connector.ga.model.response.GAStructuredResponse
import fr.vsct.tock.bot.connector.ga.model.response.GASubLine
import fr.vsct.tock.bot.connector.ga.model.response.GATransactionDecisionValueSpec
import fr.vsct.tock.bot.connector.ga.model.response.GATransactionRequirementsCheckSpec
import fr.vsct.tock.bot.engine.I18nTranslator
import mu.KotlinLogging
import java.time.Instant

private val logger = KotlinLogging.logger {}

private inline fun <reified T> ConnectorMessage.findTransactionObject(intent: GAIntent): T? =
    (this as? GARequestConnectorMessage)?.run {
        request.inputs
            .find {
                it.intent == intent.value
            }?.run {
                arguments
                    ?.map { it.extension }
                    ?.filterIsInstance<T>()
                    ?.firstOrNull()
            }

    }

/**
 * Return a [GATransactionRequirementsCheckResult] if available.
 */
fun ConnectorMessage.findTransactionRequirementsCheckInput(): GATransactionRequirementsCheckResult? =
    findTransactionObject(GAIntent.transactionRequirementsCheck)

/**
 * Return a [GATransactionDecisionValue] if available.
 */
fun ConnectorMessage.findTransactionDecisionValueInput(): GATransactionDecisionValue? =
    findTransactionObject(GAIntent.transactionDecision)


/**
 * Build a [GATransactionRequirementsCheckSpec] response.
 */
fun I18nTranslator.gaTransactionRequirementsCheck(
    orderOptions: GAOrderOptions = orderOptions(),
    paymentOptions: GAPaymentOptions? = null
): GAResponseConnectorMessage =
    gaMessage(
        GAExpectedIntent(
            GAIntent.transactionRequirementsCheck,
            GATransactionRequirementsCheckSpec(
                orderOptions,
                paymentOptions
            )
        )

    )

/**
 * Build a [GAOrderOptions].
 */
fun orderOptions(
    requestDeliveryAddress: Boolean = false,
    customerInfoProperties: Set<GACustomerInfoProperty> = setOf(GACustomerInfoProperty.EMAIL)
): GAOrderOptions =
    GAOrderOptions(
        requestDeliveryAddress = requestDeliveryAddress,
        customerInfoOptions = GACustomerInfoOptions(
            customerInfoProperties
        )
    )

/**
 * Build an [GAStructuredResponse] from an [GAOrderUpdate].
 */
fun I18nTranslator.gaOrderUpdateMessage(orderUpdate: GAOrderUpdate): GAResponseConnectorMessage =
    gaMessage(
        richResponse(
            item(
                GAStructuredResponse(
                    orderUpdate
                )
            )
        )
    )

/**
 * Build a [GAOrderUpdate].
 */
fun orderUpdate(
    googleOrderId: String,
    actionOrderId: String,
    orderState: GAOrderState,
    orderManagementActions: List<GAAction>,
    receipt: GAReceipt,
    updateTime: Instant = Instant.now(),
    totalPrice: GAPrice? = null,
    lineItemUpdates: Map<String, GALineItemUpdate> = emptyMap()
): GAOrderUpdate =
    GAOrderUpdate(
        googleOrderId,
        actionOrderId,
        orderState,
        orderManagementActions,
        receipt,
        updateTime.toString(),
        totalPrice,
        lineItemUpdates,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

/**
 * Build a [GAOrderState].
 */
fun I18nTranslator.orderState(state: GAState, label: CharSequence): GAOrderState =
    GAOrderState(state, translate(label).toString())

/**
 * Build a [GAAction].
 */
fun gaAction(type: GAActionType, button: GAButton): GAAction =
    GAAction(type, button)

/**
 * Build a [GAReceipt].
 */
fun receipt(confirmedActionOrderId: String, userVisibleOrderId: String): GAReceipt =
    GAReceipt(confirmedActionOrderId, userVisibleOrderId)

/**
 * Build a [GALineItemUpdate].
 */
fun I18nTranslator.lineItemTemplate(
    orderState: GAOrderState,
    price: GAPrice? = null,
    reason: CharSequence? = null
): GALineItemUpdate =
    GALineItemUpdate(orderState, price, translateAndReturnBlankAsNull(reason)?.toString())

/**
 * Build an [GATransactionDecisionValueSpec] message.
 */
fun I18nTranslator.gaTransactionDecision(
    proposedOrder: GAProposedOrder,
    orderOptions: GAOrderOptions,
    paymentOptions: GAPaymentOptions? = null
): GAResponseConnectorMessage =
    gaMessage(
        GAExpectedIntent(
            GAIntent.transactionDecision,
            GATransactionDecisionValueSpec(
                proposedOrder,
                orderOptions,
                paymentOptions
            )
        )
    )

/**
 * Build a [GAProposedOrder].
 */
fun proposedOrder(
    id: String,
    cart: GACart,
    otherItems: List<GALineItem>? = null,
    image: GAImage,
    termsOfServiceUrl: String,
    totalPrice: GAPrice
): GAProposedOrder =
    GAProposedOrder(
        id,
        cart,
        otherItems,
        image,
        termsOfServiceUrl,
        totalPrice
    )

/**
 * Build a [GACart].
 */
fun I18nTranslator.cart(
    id: String,
    merchant: GAMerchant? = null,
    lineItems: List<GALineItem>,
    otherItems: List<GALineItem>? = null,
    notes: CharSequence
): GACart =
    GACart(
        id,
        merchant,
        lineItems,
        otherItems,
        translate(notes).toString()
    )

/**
 * Build a [GAMerchant].
 */
fun merchant(id: String, name: String): GAMerchant = GAMerchant(id, name)

/**
 * Build a [GALineItem].
 */
fun I18nTranslator.lineItem(
    id: String,
    name: CharSequence,
    type: GALineItemType = GALineItemType.REGULAR,
    quantity: Int,
    description: CharSequence,
    image: GAImage,
    price: GAPrice,
    subLines: List<GASubLine>? = null,
    offerId: String? = null
): GALineItem =
    GALineItem(
        id,
        translate(name).toString().run {
            if(length > 100) {
                logger.warn { "line item name has more than 100 chars - remove chars after 100" }
                substring(0, 100)
            } else {
                this
            }
        },
        type,
        quantity,
        translate(description).toString(),
        image,
        price,
        subLines,
        offerId
    )

/**
 * Build a [GAPrice].
 */
fun price(
    type: GAPriceType,
    amount: GAMoney
): GAPrice =
    GAPrice(type, amount)

/**
 * Build a [GAMoney].
 */
fun money(
    currencyCode: String,
    units: String?,
    nanos: Long = 0
): GAMoney =
    GAMoney(currencyCode, units, nanos)