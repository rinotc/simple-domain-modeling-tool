package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.sdmt.test.BaseTest

class JapaneseNameTest extends BaseTest {

  "requirement" when {
    "empty value String" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          JapaneseName("")
        }
      }
    }

    "value length is 51" should {
      "throw requirement error" in {
        val value = "あ" * 51
        value.length shouldBe 51
        assertThrows[IllegalArgumentException] {
          JapaneseName(value)
        }
      }
    }

    "value length is 50" should {
      "can create instance" in {
        val value = "あ" * 50
        value.length shouldBe 50
        JapaneseName(value)
      }
    }
  }

  "validate" when {
    "empty value String" should {
      "return Left" in {
        JapaneseName.validate("") shouldBe Symbol("left")
      }
    }

    "value length is 51" should {
      "return Left" in {
        val value = "あ" * 51
        value.length shouldBe 51
        JapaneseName.validate(value) shouldBe Symbol("left")
      }
    }

    "value length is 50" should {
      "can create instance" in {
        val value = "あ" * 50
        value.length shouldBe 50
        val actual = JapaneseName.validate(value)
        actual shouldBe Symbol("right")
        actual.value.value shouldBe value
      }
    }
  }

  "equals" when {
    "same underlying value" should {
      "return true" in {
        val a = JapaneseName("日本語")
        val b = JapaneseName("日本語")
        (a == b) shouldBe true
      }
    }

    "not same underlying value" should {
      "return false" in {
        val a = JapaneseName("日本語")
        val b = JapaneseName("英語")

        (a == b) shouldBe false
      }
    }
  }
}
