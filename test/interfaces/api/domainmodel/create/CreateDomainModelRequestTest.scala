package interfaces.api.domainmodel.create

import cats.data.Validated
import dev.tchiba.test.core.BaseFunTest

class CreateDomainModelRequestTest extends BaseFunTest {

  describe("validateParameters") {
    describe("ユビキタス名のバリデーション") {
      it("ユビキタス名が空文字の時、Invalidを返す") {
        val request = CreateDomainModelRequest(
          ubiquitousName = "",
          englishName = "EnglishName",
          knowledge = "知識"
        )

        assert(request.validateParameters.isInvalid)
        inside(request.validateParameters) { case Validated.Invalid(nl) =>
          assert(nl.head._1 == "ubiquitousName")
        }
      }

      it("ユビキタス名が51文字以下の時、Invalidを返す") {
        val ubiName = "A" * 51
        val request = CreateDomainModelRequest(
          ubiquitousName = ubiName,
          englishName = "EnglishName",
          knowledge = "知識"
        )
        assert(request.validateParameters.isInvalid)
        inside(request.validateParameters) { case Validated.Invalid(nl) =>
          assert(nl.head._1 == "ubiquitousName")
        }
      }
    }

    describe("英語名のバリデーション") {
      it("英語名に日本語が含まれるとき、Invalidを返す") {
        val request = CreateDomainModelRequest(
          ubiquitousName = "ユビキタス名",
          englishName = "ユビキタス名",
          knowledge = "知識"
        )

        assert(request.validateParameters.isInvalid)
        inside(request.validateParameters) { case Validated.Invalid(nl) =>
          assert(nl.head._1 == "englishName")
        }
      }

      it("英語名が空文字の時、Invalidを返す") {
        val request = CreateDomainModelRequest(
          ubiquitousName = "ユビキタス名",
          englishName = "",
          knowledge = "知識"
        )

        assert(request.validateParameters.isInvalid)
        inside(request.validateParameters) { case Validated.Invalid(nl) =>
          assert(nl.head._1 == "englishName")
        }
      }

      it("英語名が101文字の時、Invalidを返す") {
        val engName = "A" * 101
        val request = CreateDomainModelRequest(
          ubiquitousName = "ユビキタス名",
          englishName = engName,
          knowledge = "知識"
        )

        assert(request.validateParameters.isInvalid)
        inside(request.validateParameters) { case Validated.Invalid(nl) =>
          assert(nl.head._1 == "englishName")
        }
      }
    }
  }
}
