package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.sdmt.core.boundedContext.BoundedContextOverview
import dev.tchiba.test.core.BaseWordTest

class BoundedContextOverviewTest extends BaseWordTest {

  "BoundeContextOverview Requirement" when {
    "501 length value" should {
      "throw requirement error" in {
        val value = "A" * 501
        value.length shouldBe 501
        assertThrows[IllegalArgumentException] {
          BoundedContextOverview(value)
        }
      }
    }
  }

  "equals" when {
    "compare same underlying value instance" should {
      "return true" in {
        val overviewA = BoundedContextOverview("ABC")
        val overviewB = BoundedContextOverview("ABC")

        overviewA shouldEqual overviewB
      }
    }

    "compare not same underlying value instance" should {
      "return false" in {
        val overviewA = BoundedContextOverview("ABC")
        val overviewB = BoundedContextOverview("XYZ")

        overviewA should not equal overviewB
      }
    }
  }

  "validate" when {
    "501 length String value" should {
      "return left" in {
        val value  = "A" * 501
        val actual = BoundedContextOverview.validate(value)
        actual shouldBe Symbol("left")
      }
    }

    "empty String value" should {
      "return right" in {
        val actual = BoundedContextOverview.validate("")
        actual shouldBe Symbol("right")
        actual.value.value shouldBe ""
      }
    }
  }
}
