package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.test.core.BaseWordTest

class UbiquitousNameTest extends BaseWordTest {

  "requirement" when {
    "empty value String" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          UbiquitousName("")
        }
      }
    }

    "value length is 51" should {
      "throw requirement error" in {
        val value = "あ" * 51
        value.length shouldBe 51
        assertThrows[IllegalArgumentException] {
          UbiquitousName(value)
        }
      }
    }

    "value length is 50" should {
      "can create instance" in {
        val value = "あ" * 50
        value.length shouldBe 50
        UbiquitousName(value)
      }
    }
  }

  "validate" when {
    "empty value String" should {
      "return Left" in {
        UbiquitousName.validate("") shouldBe Symbol("left")
      }
    }

    "value length is 51" should {
      "return Left" in {
        val value = "あ" * 51
        value.length shouldBe 51
        UbiquitousName.validate(value) shouldBe Symbol("left")
      }
    }

    "value length is 50" should {
      "can create instance" in {
        val value = "あ" * 50
        value.length shouldBe 50
        val actual = UbiquitousName.validate(value)
        actual shouldBe Symbol("right")
        actual.value.value shouldBe value
      }
    }
  }

  "equals" when {
    "same underlying value" should {
      "return true" in {
        val a = UbiquitousName("日本語")
        val b = UbiquitousName("日本語")
        (a == b) shouldBe true
      }
    }

    "not same underlying value" should {
      "return false" in {
        val a = UbiquitousName("日本語")
        val b = UbiquitousName("英語")

        (a == b) shouldBe false
      }
    }
  }
}
