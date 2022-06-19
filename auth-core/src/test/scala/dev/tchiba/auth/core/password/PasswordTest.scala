package dev.tchiba.auth.core.password

import dev.tchiba.test.core.BaseTest
import org.scalatestplus.mockito.MockitoSugar

import java.io.{ObjectInput, ObjectOutput}

class PasswordTest extends BaseTest with MockitoSugar {

  "readExternal" when {
    "call this method" should {
      "throw UnsupportedOperationException" in {
        val password = Password("PlainPassword123!@#")
        val oi       = mock[ObjectInput]
        assertThrows[UnsupportedOperationException] {
          password.readExternal(oi)
        }
      }
    }
  }

  "writeExternal" when {
    "call this method" should {
      "throw UnsupportedOperationException" in {
        val password = Password("PlainPassword123!!@#")
        val oo       = mock[ObjectOutput]
        assertThrows[UnsupportedOperationException] {
          password.writeExternal(oo)
        }
      }
    }
  }

  "toString" when {
    "call this method" should {
      "return masked String" in {
        val password = Password("PlainPassword123!!@#")
        password.toString shouldBe "Password(********)"
      }
    }
  }

  "hashedPassword" when {
    "read this" should {
      "not return plainPassword" in {
        val plainPassword = "PlainPassword123@#$"
        val password      = Password(plainPassword)

        password.hashedPassword should not be plainPassword
      }
    }
  }

  "verify" when {
    "verify same plainPassword's hashed password" should {
      "return true" in {
        val plainPassword = "PlainPassword123@#$"
        val passwordA     = Password(plainPassword)
        val passwordB     = Password(plainPassword)

        passwordA.hashedPassword should not be passwordB.hashedPassword
        passwordA.verify(passwordB.hashedPassword) shouldBe true
        passwordB.verify(passwordA.hashedPassword) shouldBe true
      }
    }

    "not same plainPassword's hashed password" should {
      "return false" in {
        val plainPasswordA = "PlainPassword123!@#"
        val plainPasswordB = "pLaiNpAssWorD987*&^"

        val passwordA = Password(plainPasswordA)
        val passwordB = Password(plainPasswordB)

        passwordA.verify(passwordB.hashedPassword) shouldBe false
        passwordB.verify(passwordA.hashedPassword) shouldBe false
      }
    }
  }
}
