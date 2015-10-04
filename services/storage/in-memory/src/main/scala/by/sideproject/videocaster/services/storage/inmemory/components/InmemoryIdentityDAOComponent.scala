package by.sideproject.videocaster.services.storage.inmemory.components

import by.sideproject.videocaster.services.storage.base.components.IdentityDAOComponent
import by.sideproject.videocaster.services.storage.base.dao.IdentityDAO
import by.sideproject.videocaster.services.storage.inmemory.dao.InmemoryIdentityDAO

trait InmemoryIdentityDAOComponent extends IdentityDAOComponent {

  lazy val identityDAO: IdentityDAO = new InmemoryIdentityDAO

}
