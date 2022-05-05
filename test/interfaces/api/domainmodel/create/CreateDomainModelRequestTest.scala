package interfaces.api.domainmodel.create

import dev.tchiba.sdmt.core.domainmodel.{EnglishName, JapaneseName}
import dev.tchiba.sdmt.test.BaseTest
import interfaces.json.RequestValidationError

class CreateDomainModelRequestTest extends BaseTest {

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
