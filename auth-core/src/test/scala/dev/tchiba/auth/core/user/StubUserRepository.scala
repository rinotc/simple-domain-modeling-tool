package dev.tchiba.auth.core.user

class StubUserRepository extends UserRepository {

  private var listAllUserResult: Seq[User] = Seq.empty

  private var findByIdResult: Option[User] = None

  def setListAllUserResult(result: Seq[User]): Unit = {
    listAllUserResult = result
  }

  def setFindByIdResult(result: Option[User]): Unit = {
    findByIdResult = result
  }
  def listAll(): Seq[User] = listAllUserResult

  def findById(id: UserId): Option[User] = findByIdResult

  def insert(user: User): Unit = ()

  def update(user: User): Unit = ()

  def delete(user: User): Unit = ()

  def batchInsert(users: Seq[User]): Unit = ()
}
