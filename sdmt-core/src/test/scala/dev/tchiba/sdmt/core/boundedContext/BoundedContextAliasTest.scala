package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.test.core.BaseFunSpec

class BoundedContextAliasTest extends BaseFunSpec {
  describe("BoundedContextAlias Requirement") {
    describe("空文字を渡した時") {
      it("事前条件違反で例外を投げる") {
        assertThrows[IllegalArgumentException] {
          BoundedContextAlias("")
        }
      }
    }

    describe("32文字以上の文字列を渡した時") {
      it("事前条件違反で例外を投げる") {
        assertThrows[IllegalArgumentException] {
          val over32Alias = "A" * 33
          BoundedContextAlias(over32Alias)
        }
      }
    }

    describe("英数字以外の文字列を渡した時") {
      it("事前条件違反で例外を投げる") {
        assertThrows[IllegalArgumentException] {
          BoundedContextAlias("境界づけられたコンテキストエイリアス")
        }
      }
    }
  }

  describe("equals") {
    describe("内部プロパティの値が一致する時") {
      it("trueを返す") {
        val aliasA = BoundedContextAlias("azAZ123")
        val aliasB = BoundedContextAlias("azAZ123")

        (aliasA == aliasB) shouldBe true
      }
    }

    describe("内部プロパティの値が一致しない時") {
      it("falseを返す") {
        val aliasA = BoundedContextAlias("azAZ123")
        val aliasB = BoundedContextAlias("321ZAza")

        (aliasA == aliasB) shouldBe false
      }
    }
  }

  describe("validate") {
    describe("空文字を渡した時") {
      it("Left を返す") {
        BoundedContextAlias.validate("") shouldBe Symbol("left")
      }
    }

    describe("32文字より多い文字数の文字列を渡した時") {
      it("Leftを返す") {
        val value = "A" * 33
        value.length shouldBe 33
        BoundedContextAlias.validate(value) shouldBe Symbol("left")
      }
    }

    describe("英数以外の文字を含む文字列を渡した時") {
      it("Leftを返す") {
        BoundedContextAlias.validate("境界づけられたコンテキストエイリアス") shouldBe Symbol("left")
      }
    }

    describe("1文字の文字列を渡した時") {
      it("Rightを返す") {
        BoundedContextAlias.validate("A") shouldBe Symbol("right")
      }
    }

    describe("32文字の文字列を渡した時") {
      it("Rightを返す") {
        val value = "ABCDE" + "fghij" + "KLMNO" + "pqrst" + "UVWXY" + "12345" + "67"
        value.length shouldBe 32
        val actual = BoundedContextAlias.validate(value)
        actual shouldBe Symbol("right")
        actual.value.value shouldBe value
      }
    }
  }
}
