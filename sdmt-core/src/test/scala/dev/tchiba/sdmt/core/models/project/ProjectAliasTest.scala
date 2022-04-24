package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.test.BaseTest

class ProjectAliasTest extends BaseTest {
  "ProjectAlias Requirement" when {
    "empty value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          ProjectAlias("")
        }
      }
    }

    "over 32 length value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          val over32Alias = "A" * 33
          ProjectAlias(over32Alias)
        }
      }
    }

    "not alphanumerical value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          ProjectAlias("プロジェクトエイリアス")
        }
      }
    }
  }

  "equals" when {
    "same underlying value" should {
      "return true" in {
        val aliasA = ProjectAlias("azAZ123")
        val aliasB = ProjectAlias("azAZ123")

        (aliasA == aliasB) shouldBe true
      }
    }

    "not same underlying value" should {
      "return false" in {
        val aliasA = ProjectAlias("azAZ123")
        val aliasB = ProjectAlias("321ZAza")

        (aliasA == aliasB) shouldBe false
      }
    }
  }

  "validate" when {
    "empty value" should {
      "return left" in {
        ProjectAlias.validate("").isLeft shouldBe true
      }
    }

    "over 32 length value" should {
      "return left" in {
        val value = "A" * 33
        ProjectAlias.validate(value).isLeft shouldBe true
      }
    }

    "not alphanumerical value" should {
      "return left" in {
        ProjectAlias.validate("プロジェクトエイリアス").isLeft shouldBe true
      }
    }

    "1 characters value" should {
      "return right" in {
        ProjectAlias.validate("A").isRight shouldBe true
      }
    }

    "32 characters value" should {
      "return right" in {
        val value = "ABCDE" + "fghij" + "KLMNO" + "pqrst" + "UVWXY" + "12345" + "67"
        value.length shouldBe 32
        val actual = ProjectAlias.validate(value)
        actual shouldBe Symbol("right")
        actual.value shouldBe value
      }
    }
  }
}
