package dev.tchiba.sdmt.core.models.boundedContext

import dev.tchiba.sdmt.test.BaseTest

class BoundedContextNameTest extends BaseTest {

  "BoundedContextName Requirement" when {
    "empty value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          BoundedContextName("")
        }
      }
    }

    "over 101 length value" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          val value = "A" * 101
          BoundedContextName(value)
        }
      }
    }

    "1 length value" should {
      "can create instance" in {
        BoundedContextName("A").value shouldBe "A"
      }
    }

    "100 length value" should {
      "can create instance" in {
        val value = "A" * 100
        BoundedContextName(value).value shouldBe value
      }
    }
  }

  "equal" when {
    "same value instances" should {
      "be equal" in {
        BoundedContextName("AbcDefアイウエオ") shouldEqual BoundedContextName("AbcDefアイウエオ")
      }
    }

    "not same value instance" should {
      "not be equal" in {
        BoundedContextName("ABC") should not be BoundedContextName("アイウ")
      }
    }
  }

  "validate" when {
    "empty value" should {
      "return left" in {
        val actual = BoundedContextName.validate("")
        actual shouldBe Symbol("left")
      }
    }

    "over 101 length value" should {
      "return left" in {
        val actual = BoundedContextName.validate("A" * 101)
        actual shouldBe Symbol("left")
      }
    }

    "1 characters value" should {
      "return right" in {
        val actual = BoundedContextName.validate("A")
        actual shouldBe Symbol("right")
        actual.value.value shouldBe "A"
      }
    }
  }
}
