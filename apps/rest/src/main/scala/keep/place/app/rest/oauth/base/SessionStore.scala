package keep.place.app.rest.oauth.base

import java.util.UUID

import keep.place.model.auth.Identity

import scala.Predef._

trait SessionStore {

  var sessionStore: Map[String, Identity] = Map()

  def addSession(sessionId: String, identity: Identity) =
    sessionStore += sessionId -> identity

  def getSession(sessionId: String): Option[Identity] =
    sessionStore.get(sessionId)


  def removeSession(sessionId: String) =
    sessionStore -= sessionId

}



