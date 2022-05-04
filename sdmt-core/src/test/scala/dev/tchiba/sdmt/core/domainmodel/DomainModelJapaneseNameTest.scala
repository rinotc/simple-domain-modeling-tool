package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.sdmt.test.BaseTest

class DomainModelJapaneseNameTest extends BaseTest {

  "requirement" when {
    "empty value String" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          DomainModelJapaneseName("")
        }
      }
    }

    "value length is 51" should {
      "throw requirement error" in {
        val value = "あ" * 51
        value.length shouldBe 51
        assertThrows[IllegalArgumentException] {
          DomainModelJapaneseName(value)
        }
      }
    }

    "value length is 50" should {
      "can create instance" in {
        val value = "あ" * 50
        value.length shouldBe 50
        DomainModelJapaneseName(value)
      }
    }
  }

  "validate" when {
    "empty value String" should {
      "return Left" in {
        DomainModelJapaneseName.validate("") shouldBe Symbol("left")
      }
    }

    "value length is 51" should {
      "return Left" in {
        val value = "あ" * 51
        value.length shouldBe 51
        DomainModelJapaneseName.validate(value) shouldBe Symbol("left")
      }
    }

    "value length is 50" should {
      "can create instance" in {
        val value = "あ" * 50
        value.length shouldBe 50
        val actual = DomainModelJapaneseName.validate(value)
        actual shouldBe Symbol("right")
        actual.value.value shouldBe value
      }
    }
  }

  "equals" when {
    "same underlying value" should {
      "return true" in {
        val a = DomainModelJapaneseName("日本語")
        val b = DomainModelJapaneseName("日本語")
        (a == b) shouldBe true
      }
    }

    "not same underlying value" should {
      "return false" in {
        val a = DomainModelJapaneseName("日本語")
        val b = DomainModelJapaneseName("英語")

        (a == b) shouldBe false
      }
    }
  }
}
