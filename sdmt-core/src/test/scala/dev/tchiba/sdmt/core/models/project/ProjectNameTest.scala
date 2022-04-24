package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.test.BaseTest

class ProjectNameTest extends BaseTest {

  "ProjectName Requirement" when {
    "empty value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          ProjectName("")
        }
      }
    }

    "over 101 length value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          val value = "A" * 101
          ProjectName(value)
        }
      }
    }

    "1 length value" should {
      "can create instance" in {
        ProjectName("A").value shouldBe "A"
      }
    }

    "100 length value" should {
      "can create instance" in {
        val value = "A" * 100
        ProjectName(value).value shouldBe value
      }
    }
  }

  "equal" when {
    "same value instances" should {
      "be equal" in {
        ProjectName("AbcDefアイウエオ") shouldEqual ProjectName("AbcDefアイウエオ")
      }
    }

    "not same value instance" should {
      "not be equal" in {
        ProjectName("ABC") should not be ProjectName("アイウ")
      }
    }
  }

  "validate" when {
    "empty value" should {
      "return left" in {
        val actual = ProjectName.validate("")
        actual shouldBe Symbol("left")
      }
    }

    "over 101 length value" should {
      "return left" in {
        val actual = ProjectName.validate("A" * 101)
        actual shouldBe Symbol("left")
      }
    }

    "1 characters value" should {
      "return right" in {
        val actual = ProjectName.validate("A")
        actual shouldBe Symbol("right")
        actual.value.value shouldBe "A"
      }
    }
  }
}
