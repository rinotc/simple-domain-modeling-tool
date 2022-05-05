package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.domainmodel.{EnglishName, JapaneseName}
import interfaces.json.RequestValidationError
import org.scalatestplus.play.PlaySpec

class CreateDomainModelRequestTest extends PlaySpec {

  "primary constructor" when {
    "invalid japaneseName request" should {
      "throw RequestValidationError" in {
        val japaneseName = "あ" * 51
        assertThrows[IllegalArgumentException] {
          JapaneseName(japaneseName)
        }

        assertThrows[RequestValidationError] {
          CreateDomainModelRequest(
            japaneseName,
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
