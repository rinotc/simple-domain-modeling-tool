package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.test.core.BaseWordTest

class BoundedContextAliasTest extends BaseWordTest {
  "BoundedContextAlias Requirement" when {
    "empty value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          BoundedContextAlias("")
        }
      }
    }

    "over 32 length value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          val over32Alias = "A" * 33
          BoundedContextAlias(over32Alias)
        }
      }
    }

    "not alphanumerical value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          BoundedContextAlias("境界づけられたコンテキストエイリアス")
        }
      }
    }
  }

  "equals" when {
    "same underlying value" should {
      "return true" in {
        val aliasA = BoundedContextAlias("azAZ123")
        val aliasB = BoundedContextAlias("azAZ123")

        (aliasA == aliasB) shouldBe true
      }
    }

    "not same underlying value" should {
      "return false" in {
        val aliasA = BoundedContextAlias("azAZ123")
        val aliasB = BoundedContextAlias("321ZAza")

        (aliasA == aliasB) shouldBe false
      }
    }
  }

  "validate" when {
    "empty value" should {
      "return left" in {
        BoundedContextAlias.validate("").isLeft shouldBe true
      }
    }

    "over 32 length value" should {
      "return left" in {
        val value = "A" * 33
        BoundedContextAlias.validate(value).isLeft shouldBe true
      }
    }

    "not alphanumerical value" should {
      "return left" in {
        BoundedContextAlias.validate("境界づけられたコンテキストエイリアス").isLeft shouldBe true
      }
    }

    "1 characters value" should {
      "return right" in {
        BoundedContextAlias.validate("A").isRight shouldBe true
      }
    }

    "32 characters value" should {
      "return right" in {
        val value = "ABCDE" + "fghij" + "KLMNO" + "pqrst" + "UVWXY" + "12345" + "67"
        value.length shouldBe 32
        val actual = BoundedContextAlias.validate(value)
        actual shouldBe Symbol("right")
        actual.value.value shouldBe value
      }
    }
  }
}
