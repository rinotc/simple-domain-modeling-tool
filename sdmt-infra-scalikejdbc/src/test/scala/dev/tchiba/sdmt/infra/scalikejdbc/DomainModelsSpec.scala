package dev.tchiba.sdmt.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class DomainModelsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val dm = DomainModels.syntax("dm")

  behavior of "DomainModels"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = DomainModels.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = DomainModels.findBy(sqls.eq(dm.domainModelId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = DomainModels.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = DomainModels.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = DomainModels.findAllBy(sqls.eq(dm.domainModelId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = DomainModels.countBy(sqls.eq(dm.domainModelId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = DomainModels.create(domainModelId = "MyString", boundedContextId = "MyString", japaneseName = "MyString", englishName = "MyString", specification = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = DomainModels.findAll().head
    // TODO modify something
    val modified = entity
    val updated = DomainModels.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = DomainModels.findAll().head
    val deleted = DomainModels.destroy(entity)
    deleted should be(1)
    val shouldBeNone = DomainModels.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = DomainModels.findAll()
    entities.foreach(e => DomainModels.destroy(e))
    val batchInserted = DomainModels.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
