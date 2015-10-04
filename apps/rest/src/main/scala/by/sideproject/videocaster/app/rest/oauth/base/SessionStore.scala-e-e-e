package by.sideproject.videocaster.app.rest.oauth.base

import java.util.UUID

import by.sideproject.videocaster.model.auth.Identity

import scala.Predef._

trait SessionStore {

  var sessionStore: Map[String, Identity] = Map()

  def addSession(sessionId: String, identity: Identity) =
    sessionStore += sessionId -> identity

  def getSession(sessionId: String): Option[Identity] =
    sessionStore.get(sessionId)


  def removeSession(sessionId: String) =
    sessionStore -= sessionId

  def getRandomSessionId: String = UUID.randomUUID().toString

}



