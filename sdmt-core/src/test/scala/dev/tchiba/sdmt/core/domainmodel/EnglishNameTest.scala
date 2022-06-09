package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.test.core.BaseTest

class EnglishNameTest extends BaseTest {

  "requirement" when {
    "empty String" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          EnglishName("")
        }
      }
    }

    "101 Length String" should {
      "throw requirement error" in {
        val value = "A" * 101
        value.length shouldBe 101
        assertThrows[IllegalArgumentException] {
          EnglishName(value)
        }
      }
    }

    "not alphabet value contain" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          EnglishName("ABCアイウeo")
        }
      }
    }

    "contains space" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          EnglishName("abc DEF 123")
        }
      }
    }
  }

  "equals" when {
    "same underlying value" should {
      "return true" in {
        val a = EnglishName("ABC")
        val b = EnglishName("ABC")
        (a == b) shouldBe true
      }
    }

    "not same underlying value" should {
      "return false" in {
        val a = EnglishName("ABC")
        val b = EnglishName("DEF")
        (a == b) shouldBe false
      }
    }
  }

  "validate" when {
    "empty String" should {
      "return Left" in {
        val emptyValue = ""
        EnglishName.validate(emptyValue) shouldBe Symbol("left")
      }
    }

    "101 length value" should {
      "return Left" in {
        val value = "A" * 101
        value.length shouldBe 101
        EnglishName.validate(value) shouldBe Symbol("left")
      }
    }

    "100 length alphanumerical value" should {
      "return Right" in {
        val value = "A" * 100
        value.length shouldBe 100
        val actual = EnglishName.validate(value)
        actual.value.value shouldBe value
      }
    }

    "not alphanumerical value contain" should {
      "return Left" in {
        val value = "ABCアイウefg"
        EnglishName.validate(value) shouldBe Symbol("left")
      }
    }

    "value contains space" should {
      "return Left" in {
        val value = "ABC DEF"
        EnglishName.validate(value) shouldBe Symbol("left")
      }
    }
  }
}
