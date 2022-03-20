package dev.tchiba.dmwa.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class ProjectSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val p = Project.syntax("p")

  behavior of "Project"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Project.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Project.findBy(sqls.eq(p.projectId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Project.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Project.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Project.findAllBy(sqls.eq(p.projectId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Project.countBy(sqls.eq(p.projectId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Project.create(projectId = "MyString", projectAlias = "MyString", projectName = "MyString", projectOverview = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Project.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Project.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Project.findAll().head
    val deleted = Project.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Project.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Project.findAll()
    entities.foreach(e => Project.destroy(e))
    val batchInserted = Project.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
