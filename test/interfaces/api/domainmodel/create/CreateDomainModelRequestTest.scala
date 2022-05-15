package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.domainmodel.{EnglishName, UbiquitousName}
import interfaces.json.RequestValidationError
import org.scalatestplus.play.PlaySpec

class CreateDomainModelRequestTest extends PlaySpec {

  "primary constructor" when {
    "invalid ubiquitousName request" should {
      "throw RequestValidationError" in {
        val ubiquitousName = "あ" * 51
        assertThrows[IllegalArgumentException] {
          UbiquitousName(ubiquitousName)
        }

        assertThrows[RequestValidationError] {
          CreateDomainModelRequest(
            ubiquitousName,
            "EnglishName",
            "Specification"
          )
        }
      }
    }

    "invalid EnglishName request" should {
      "throw RequestValidationError" in {
        val englishName = "アイウ"
        assertThrows[IllegalArgumentException] {
          EnglishName(englishName)
        }

        assertThrows[RequestValidationError] {
          CreateDomainModelRequest(
            "日本語名",
            englishName,
            "Specification"
          )
        }
      }
    }
  }
}
