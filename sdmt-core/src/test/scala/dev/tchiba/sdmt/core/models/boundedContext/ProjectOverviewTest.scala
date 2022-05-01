package dev.tchiba.sdmt.core.models.boundedContext

import dev.tchiba.sdmt.test.BaseTest

class ProjectOverviewTest extends BaseTest {

  "ProjectOverview Requirement" when {
    "501 length value" should {
      "throw requirement error" in {
        val value = "A" * 501
        value.length shouldBe 501
        assertThrows[IllegalArgumentException] {
          ProjectOverview(value)
        }
      }
    }
  }

  "equals" when {
    "compare same underlying value instance" should {
      "return true" in {
        val overviewA = ProjectOverview("ABC")
        val overviewB = ProjectOverview("ABC")

        overviewA shouldEqual overviewB
      }
    }

    "compare not same underlying value instance" should {
      "return false" in {
        val overviewA = ProjectOverview("ABC")
        val overviewB = ProjectOverview("XYZ")

        overviewA should not equal overviewB
      }
    }
  }

  "validate" when {
    "501 length String value" should {
      "return left" in {
        val value  = "A" * 501
        val actual = ProjectOverview.validate(value)
        actual shouldBe Symbol("left")
      }
    }

    "empty String value" should {
      "return right" in {
        val actual = ProjectOverview.validate("")
        actual shouldBe Symbol("right")
        actual.value.value shouldBe ""
      }
    }
  }
}
