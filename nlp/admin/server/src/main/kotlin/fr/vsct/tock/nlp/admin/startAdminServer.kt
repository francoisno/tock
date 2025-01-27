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

package fr.vsct.tock.nlp.admin

import com.github.salomonbrys.kodein.Kodein
import fr.vsct.tock.nlp.front.ioc.FrontIoc
import fr.vsct.tock.shared.vertx.vertx
import fr.vsct.tock.translator.noop.noOpTranslatorModule

fun main() {
    startAdminServer(noOpTranslatorModule)
}

fun startAdminServer(vararg modules: Kodein.Module) {
    FrontIoc.setup(*modules)
    vertx.deployVerticle(AdminVerticle())
}
