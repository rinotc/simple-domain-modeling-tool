package dev.tchiba.sdmt.infra.scalikejdbc

import scalikejdbc._

case class AuthInfos(
  authInfoId: String,
  userId: String,
  hashedPassword: String,
  salt: String) {

  def save()(implicit session: DBSession = AuthInfos.autoSession): AuthInfos = AuthInfos.save(this)(session)

  def destroy()(implicit session: DBSession = AuthInfos.autoSession): Int = AuthInfos.destroy(this)(session)

}


object AuthInfos extends SQLSyntaxSupport[AuthInfos] {

  override val schemaName = Some("public")

  override val tableName = "auth_infos"

  override val columns = Seq("auth_info_id", "user_id", "hashed_password", "salt")

  def apply(ai: SyntaxProvider[AuthInfos])(rs: WrappedResultSet): AuthInfos = apply(ai.resultName)(rs)
  def apply(ai: ResultName[AuthInfos])(rs: WrappedResultSet): AuthInfos = new AuthInfos(
    authInfoId = rs.get(ai.authInfoId),
    userId = rs.get(ai.userId),
    hashedPassword = rs.get(ai.hashedPassword),
    salt = rs.get(ai.salt)
  )

  val ai = AuthInfos.syntax("ai")

  override val autoSession = AutoSession

  def find(authInfoId: String)(implicit session: DBSession = autoSession): Option[AuthInfos] = {
    withSQL {
      select.from(AuthInfos as ai).where.eq(ai.authInfoId, authInfoId)
    }.map(AuthInfos(ai.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AuthInfos] = {
    withSQL(select.from(AuthInfos as ai)).map(AuthInfos(ai.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(AuthInfos as ai)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AuthInfos] = {
    withSQL {
      select.from(AuthInfos as ai).where.append(where)
    }.map(AuthInfos(ai.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AuthInfos] = {
    withSQL {
      select.from(AuthInfos as ai).where.append(where)
    }.map(AuthInfos(ai.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(AuthInfos as ai).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    authInfoId: String,
    userId: String,
    hashedPassword: String,
    salt: String)(implicit session: DBSession = autoSession): AuthInfos = {
    withSQL {
      insert.into(AuthInfos).namedValues(
        column.authInfoId -> authInfoId,
        column.userId -> userId,
        column.hashedPassword -> hashedPassword,
        column.salt -> salt
      )
    }.update.apply()

    AuthInfos(
      authInfoId = authInfoId,
      userId = userId,
      hashedPassword = hashedPassword,
      salt = salt)
  }

  def batchInsert(entities: collection.Seq[AuthInfos])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("authInfoId") -> entity.authInfoId,
        Symbol("userId") -> entity.userId,
        Symbol("hashedPassword") -> entity.hashedPassword,
        Symbol("salt") -> entity.salt))
    SQL("""insert into auth_infos(
      auth_info_id,
      user_id,
      hashed_password,
      salt
    ) values (
      {authInfoId},
      {userId},
      {hashedPassword},
      {salt}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: AuthInfos)(implicit session: DBSession = autoSession): AuthInfos = {
    withSQL {
      update(AuthInfos).set(
        column.authInfoId -> entity.authInfoId,
        column.userId -> entity.userId,
        column.hashedPassword -> entity.hashedPassword,
        column.salt -> entity.salt
      ).where.eq(column.authInfoId, entity.authInfoId)
    }.update.apply()
    entity
  }

  def destroy(entity: AuthInfos)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(AuthInfos).where.eq(column.authInfoId, entity.authInfoId) }.update.apply()
  }

}
