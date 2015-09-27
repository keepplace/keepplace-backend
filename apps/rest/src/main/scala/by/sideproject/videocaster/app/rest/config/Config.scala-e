package by.sideproject.videocaster.app.rest.config

import com.typesafe.config.ConfigFactory

object Config {
  private val config = ConfigFactory.load()

  import config._

  val domain = getString("application.external-host")
  val bindInterface = getString("application.bind.interface")
  val bindPort = getInt("application.bind.port")

}
