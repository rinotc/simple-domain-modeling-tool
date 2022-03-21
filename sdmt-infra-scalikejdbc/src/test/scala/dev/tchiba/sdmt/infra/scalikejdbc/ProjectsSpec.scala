package dev.tchiba.sdmt.infra.scalikejdbc

import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc._


class ProjectsSpec extends FixtureAnyFlatSpec with Matchers with AutoRollback {
  val p = Projects.syntax("p")

  behavior of "Projects"

  it should "find by primary keys" in { implicit session =>
    val maybeFound = Projects.find("MyString")
    maybeFound.isDefined should be(true)
  }
  it should "find by where clauses" in { implicit session =>
    val maybeFound = Projects.findBy(sqls.eq(p.projectId, "MyString"))
    maybeFound.isDefined should be(true)
  }
  it should "find all records" in { implicit session =>
    val allResults = Projects.findAll()
    allResults.size should be >(0)
  }
  it should "count all records" in { implicit session =>
    val count = Projects.countAll()
    count should be >(0L)
  }
  it should "find all by where clauses" in { implicit session =>
    val results = Projects.findAllBy(sqls.eq(p.projectId, "MyString"))
    results.size should be >(0)
  }
  it should "count by where clauses" in { implicit session =>
    val count = Projects.countBy(sqls.eq(p.projectId, "MyString"))
    count should be >(0L)
  }
  it should "create new record" in { implicit session =>
    val created = Projects.create(projectId = "MyString", projectAlias = "MyString", projectName = "MyString", projectOverview = "MyString")
    created should not be(null)
  }
  it should "save a record" in { implicit session =>
    val entity = Projects.findAll().head
    // TODO modify something
    val modified = entity
    val updated = Projects.save(modified)
    updated should not equal(entity)
  }
  it should "destroy a record" in { implicit session =>
    val entity = Projects.findAll().head
    val deleted = Projects.destroy(entity)
    deleted should be(1)
    val shouldBeNone = Projects.find("MyString")
    shouldBeNone.isDefined should be(false)
  }
  it should "perform batch insert" in { implicit session =>
    val entities = Projects.findAll()
    entities.foreach(e => Projects.destroy(e))
    val batchInserted = Projects.batchInsert(entities)
    batchInserted.size should be >(0)
  }
}
