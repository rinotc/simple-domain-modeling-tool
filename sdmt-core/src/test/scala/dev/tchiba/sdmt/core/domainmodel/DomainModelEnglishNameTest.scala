package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.sdmt.test.BaseTest

class DomainModelEnglishNameTest extends BaseTest {

  "requirement" when {
    "empty String" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          DomainModelEnglishName("")
        }
      }
    }

    "101 Length String" should {
      "throw requirement error" in {
        val value = "A" * 101
        value.length shouldBe 101
        assertThrows[IllegalArgumentException] {
          DomainModelEnglishName(value)
        }
      }
    }

    "not alphabet value contain" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          DomainModelEnglishName("ABCアイウeo")
        }
      }
    }

    "contains space" should {
      "throw requirement error" in {
        assertThrows[IllegalArgumentException] {
          DomainModelEnglishName("abc DEF 123")
        }
      }
    }
  }

  "equals" when {
    "same underlying value" should {
      "return true" in {
        val a = DomainModelEnglishName("ABC")
        val b = DomainModelEnglishName("ABC")
        (a == b) shouldBe true
      }
    }

    "not same underlying value" should {
      "return false" in {
        val a = DomainModelEnglishName("ABC")
        val b = DomainModelEnglishName("DEF")
        (a == b) shouldBe false
      }
    }
  }

  "validate" when {
    "empty String" should {
      "return Left" in {
        val emptyValue = ""
        DomainModelEnglishName.validate(emptyValue) shouldBe Symbol("left")
      }
    }

    "101 length value" should {
      "return Left" in {
        val value = "A" * 101
        value.length shouldBe 101
        DomainModelEnglishName.validate(value) shouldBe Symbol("left")
      }
    }

    "100 length alphanumerical value" should {
      "return Right" in {
        val value = "A" * 100
        value.length shouldBe 100
        val actual = DomainModelEnglishName.validate(value)
        actual.value.value shouldBe value
      }
    }

    "not alphanumerical value contain" should {
      "return Left" in {
        val value = "ABCアイウefg"
        DomainModelEnglishName.validate(value) shouldBe Symbol("left")
      }
    }

    "value contains space" should {
      "return Left" in {
        val value = "ABC DEF"
        DomainModelEnglishName.validate(value) shouldBe Symbol("left")
      }
    }
  }
}
