package dev.tchiba.sdmt.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class BoundedContextsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val bc = BoundedContexts.syntax("bc")

  behavior of "BoundedContexts"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = BoundedContexts.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = BoundedContexts.findBy(sqls.eq(bc.boundedContextId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = BoundedContexts.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = BoundedContexts.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = BoundedContexts.findAllBy(sqls.eq(bc.boundedContextId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = BoundedContexts.countBy(sqls.eq(bc.boundedContextId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = BoundedContexts.create(boundedContextId = "MyString", boundedContextAlias = "MyString", boundedContextName = "MyString", boundedContextOverview = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = BoundedContexts.findAll().head
    // TODO modify something
    val modified = entity
    val updated = BoundedContexts.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = BoundedContexts.findAll().head
    val deleted = BoundedContexts.destroy(entity)
    deleted should be(1)
    val shouldBeNone = BoundedContexts.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = BoundedContexts.findAll()
    entities.foreach(e => BoundedContexts.destroy(e))
    val batchInserted = BoundedContexts.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
