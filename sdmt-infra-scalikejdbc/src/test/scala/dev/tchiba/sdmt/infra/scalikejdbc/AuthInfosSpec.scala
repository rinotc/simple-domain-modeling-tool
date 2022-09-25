package dev.tchiba.sdmt.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class AuthInfosSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val ai = AuthInfos.syntax("ai")

  behavior of "AuthInfos"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = AuthInfos.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = AuthInfos.findBy(sqls.eq(ai.authInfoId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = AuthInfos.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = AuthInfos.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = AuthInfos.findAllBy(sqls.eq(ai.authInfoId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = AuthInfos.countBy(sqls.eq(ai.authInfoId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = AuthInfos.create(authInfoId = "MyString", userId = "MyString", hashedPassword = "MyString", salt = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = AuthInfos.findAll().head
    // TODO modify something
    val modified = entity
    val updated = AuthInfos.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = AuthInfos.findAll().head
    val deleted = AuthInfos.destroy(entity)
    deleted should be(1)
    val shouldBeNone = AuthInfos.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = AuthInfos.findAll()
    entities.foreach(e => AuthInfos.destroy(e))
    val batchInserted = AuthInfos.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
