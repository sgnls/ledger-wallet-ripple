package co.ledger.wallet.web.ripple.controllers.onboarding

import biz.enef.angulate.Module.RichModule
import biz.enef.angulate.{Controller, Scope}
import biz.enef.angulate.core.{JQLite, Location}
import co.ledger.wallet.web.ripple.components.WindowManager
import co.ledger.wallet.web.ripple.services.{DeviceService, SessionService, WindowService}

import scala.scalajs.js

/**
  *
  * SplitDisclaimerController
  * ledger-wallet-ripple-chrome
  *
  * Created by Pierre Pollastri on 04/08/2016.
  *
  * The MIT License (MIT)
  *
  * Copyright (c) 2016 Ledger
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  *
  */
class SplitDisclaimerController(override val windowService: WindowService,
                                deviceService: DeviceService,
                                $location: Location,
                                $route: js.Dynamic,
                                sessionService: SessionService,
                                $scope: Scope,
                                $element: JQLite,
                                $routeParams: js.Dictionary[String])
  extends Controller with OnBoardingController {

  var balance = $routeParams("balance")

  def continue(): Unit = {
    $location.path("/account/0")
    $route.reload()
  }

  def openHelpCenter(): Unit = {
    WindowManager.open("https://ledger.groovehq.com/knowledge_base/topics/ripple-classic-etc-important-notice")
  }

}

object SplitDisclaimerController {
  def init(module: RichModule) = module.controllerOf[SplitDisclaimerController]("SplitDisclaimerController")
}