package dev.tchiba.sdmt.infra.scalikejdbc

import scalikejdbc._
import java.time.{LocalDateTime}

case class Users(
  userId: String,
  userName: String,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime) {

  def save()(implicit session: DBSession = Users.autoSession): Users = Users.save(this)(session)

  def destroy()(implicit session: DBSession = Users.autoSession): Int = Users.destroy(this)(session)

}


object Users extends SQLSyntaxSupport[Users] {

  override val schemaName = Some("public")

  override val tableName = "users"

  override val columns = Seq("user_id", "user_name", "created_at", "updated_at")

  def apply(u: SyntaxProvider[Users])(rs: WrappedResultSet): Users = apply(u.resultName)(rs)
  def apply(u: ResultName[Users])(rs: WrappedResultSet): Users = new Users(
    userId = rs.get(u.userId),
    userName = rs.get(u.userName),
    createdAt = rs.get(u.createdAt),
    updatedAt = rs.get(u.updatedAt)
  )

  val u = Users.syntax("u")

  override val autoSession = AutoSession

  def find(userId: String)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.eq(u.userId, userId)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Users] = {
    withSQL(select.from(Users as u)).map(Users(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Users as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Users] = {
    withSQL {
      select.from(Users as u).where.append(where)
    }.map(Users(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Users as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: String,
    userName: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      insert.into(Users).namedValues(
        column.userId -> userId,
        column.userName -> userName,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.update.apply()

    Users(
      userId = userId,
      userName = userName,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: collection.Seq[Users])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("userId") -> entity.userId,
        Symbol("userName") -> entity.userName,
        Symbol("createdAt") -> entity.createdAt,
        Symbol("updatedAt") -> entity.updatedAt))
    SQL("""insert into users(
      user_id,
      user_name,
      created_at,
      updated_at
    ) values (
      {userId},
      {userName},
      {createdAt},
      {updatedAt}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Users)(implicit session: DBSession = autoSession): Users = {
    withSQL {
      update(Users).set(
        column.userId -> entity.userId,
        column.userName -> entity.userName,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: Users)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Users).where.eq(column.userId, entity.userId) }.update.apply()
  }

}
