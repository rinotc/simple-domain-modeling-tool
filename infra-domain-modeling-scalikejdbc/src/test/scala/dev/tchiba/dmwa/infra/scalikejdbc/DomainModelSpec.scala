package dev.tchiba.dmwa.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class DomainModelSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val dm = DomainModel.syntax("dm")

  behavior of "DomainModel"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = DomainModel.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = DomainModel.findBy(sqls.eq(dm.domainModelId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = DomainModel.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = DomainModel.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = DomainModel.findAllBy(sqls.eq(dm.domainModelId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = DomainModel.countBy(sqls.eq(dm.domainModelId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = DomainModel.create(domainModelId = "MyString", projectId = "MyString", japaneseName = "MyString", englishName = "MyString", specification = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = DomainModel.findAll().head
    // TODO modify something
    val modified = entity
    val updated = DomainModel.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = DomainModel.findAll().head
    val deleted = DomainModel.destroy(entity)
    deleted should be(1)
    val shouldBeNone = DomainModel.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = DomainModel.findAll()
    entities.foreach(e => DomainModel.destroy(e))
    val batchInserted = DomainModel.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
