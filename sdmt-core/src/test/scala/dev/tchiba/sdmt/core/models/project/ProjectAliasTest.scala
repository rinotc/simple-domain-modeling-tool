package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.test.BaseTest

class ProjectAliasTest extends BaseTest {
  "ProjectAlias" when {
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
}
