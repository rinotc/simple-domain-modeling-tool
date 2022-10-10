package dev.tchiba.auth.core.password

import dev.tchiba.test.core.BaseFunTest
import org.scalatestplus.mockito.MockitoSugar

import java.io.{ObjectInput, ObjectOutput}

class PasswordTest extends BaseFunTest with MockitoSugar {

  describe("仕様のテスト") {
    describe("パスワードは8文字以上50文字以下の英、数、記号であり英大文字、小文字、数字、記号を少なくとも一つ含まなければならない") {
      it("パスワードが7文字の時、例外を投げる") {
        val plainPassword = "Abc123@"
        assert(plainPassword.length == 7)
        assertThrows[IllegalArgumentException] {
          Password(plainPassword)
        }
      }

      it("パスワードが8文字の時、インスタンスが生成できる") {
        val plainPassword = "Abc123@#"
        assert(plainPassword.length == 8)
        assert(Password(plainPassword).isInstanceOf[Password])
      }

      it("パスワードが51文字の時、例外を投げる") {
        val plainPassword = "Abc123@#$%" * 5 + "x"
        assert(plainPassword.length == 51)
        assertThrows[IllegalArgumentException] {
          Password(plainPassword)
        }
      }

      it("パスワードが50文字の時、インスタンスが生成できる") {
        val plainPassword = "Abc123@#$%" * 5
        assert(plainPassword.length == 50)
        assert(Password(plainPassword).isInstanceOf[Password])
      }

      ignore("パスワードに日本語が含まれるとき、例外を投げる") {
        val plainPassword = "Abc123@あああ"
        assertThrows[IllegalArgumentException] {
          Password(plainPassword)
        }
      }

      it("パスワードに英大文字を含まない時、例外を投げる") {
        val plainPassword = "abc123@#$"
        assertThrows[IllegalArgumentException] {
          Password(plainPassword)
        }
      }

      it("パスワードに英小文字を含まない時、例外を投げる") {
        val plainPassword = "ABC123@#$"
        assertThrows[IllegalArgumentException] {
          Password(plainPassword)
        }
      }

      it("パスワードに数字を含まない時、例外を投げる") {
        val plainPassword = "AbcDef@#$"
        assertThrows[IllegalArgumentException] {
          Password(plainPassword)
        }
      }

      it("パスワードに記号を含まない時、例外を投げる") {
        val plainPassword = "AbcDef123"
        assertThrows[IllegalArgumentException] {
          Password(plainPassword)
        }
      }
    }
  }

  describe("オブジェクトのシリアライズ化をすることはできない") {
    it("readExternalメソッドを呼ぶと、UnsupportedOperationException を投げる") {
      val password = Password("PlainPassword123!@#")
      val oi       = mock[ObjectInput]
      assertThrows[UnsupportedOperationException] {
        password.readExternal(oi)
      }
    }

    it("writeExternalメソッドを呼ぶと、UnsupportedOperationExceptionを投げる") {
      val password = Password("PlainPassword123!!@#")
      val oo       = mock[ObjectOutput]
      assertThrows[UnsupportedOperationException] {
        password.writeExternal(oo)
      }
    }
  }

  describe("toStringメソッドは内容を出力しない") {
    it("toStringメソッドを読んだ時、マスクされた文字列を返す") {
      val password = Password("PlainPassword123!!@#")
      assert(password.toString == "Password(********)")
    }
  }

  describe("hashedPassword") {
    it("ハッシュ化されたパスワードは、元のパスワードとは一致しない") {
      val plainPassword = "PlainPassword123@#$"
      val password      = Password(plainPassword)

      assert(password.hashedPassword.hashedPassword != plainPassword)
    }
  }

  describe("verifyメソッドのテスト") {
    describe("同じプレーンパスワードで生成されたPasswordインスタンスを比較した時") {
      val plainPassword = "PlainPassword123@#$"
      val passwordA     = Password(plainPassword)
      val passwordB     = Password(plainPassword)

      it("ハッシュ化済みパスワードは異なる") {
        assert(passwordA.hashedPassword != passwordB.hashedPassword)
      }

      it("それぞれのverifyメソッドにハッシュ化済みパスワードを渡した時、trueを返す") {
        assert(passwordA.verify(passwordB.hashedPassword))
        assert(passwordB.verify(passwordA.hashedPassword))
      }
    }

    describe("異なるプレーンパスワードで生成されたPasswordインスタンスを比較した時") {
      val plainPasswordA = "PlainPassword123!@#"
      val plainPasswordB = "pLaiNpAssWorD987*&^"

      "a" shouldBe "a"

      val passwordA = Password(plainPasswordA)
      val passwordB = Password(plainPasswordB)
      it("verifyメソッドはfalseを返す") {
        assert(!passwordA.verify(passwordB.hashedPassword))
        assert(!passwordB.verify(passwordA.hashedPassword))
      }
    }
  }
}
