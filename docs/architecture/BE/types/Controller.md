# Controller

このスタイルの場合、Controllerのやることはびっくりするほど変わらない。
`Request` を受け取って、 `Input` に変換し、 `UseCase` にそれを渡して、返ってきた `Output` に基づいて
`Response` を決める。ほぼ全てこのパターンの実装になる。

```scala
import interfaces.api.domainmodel.create.CreateDomainModelRequest
import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.domainmodel.create.{CreateDomainModelOutput, CreateDomainModelUseCase}
import interfaces.api.QueryValidator
import interfaces.api.domainmodel.json.DomainModelResponse
import interfaces.json.error.ErrorResponse
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

final class CreateDomainModelApiController @Inject() (
    cc: ControllerComponents,
    createDomainModelUseCase: CreateDomainModelUseCase
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit private val parser: PlayBodyParsers = cc.parsers
  
  // 最初から1Controller 1メソッドにしてしまうのが良い気がしてきている。
  // 最初からクラスを分けるのは大したコストではない。
  // 何より、UnitTestが書きやすいし、コード設計への関心がよっぽど強い開発者でない限り、
  // fatになったControllerを分割するモチベーションは出ないだろう。
  // そもそも、命名に無頓着な開発者は思っているよりも多く、そもそもクラスを分けることができない（しようとしない）
  // ので、最初から命名に気をつけないといけないような実装にして仕舞えばいい。
  def action(id: String): Action[CreateDomainModelRequest] = Action(CreateDomainModelRequest.validateJson) {
    implicit request =>
      QueryValidator.sync {
        BoundedContextId.validate(id)
      } { boundedContextId =>
        val input = request.body.input(boundedContextId)
        createDomainModelUseCase.handle(input) match {
          // こんな感じで`Output`のパターンマッチで書ける。一眼でどういう時にどのようなレスポンスを返すのかが分かる。
          case CreateDomainModelOutput.NoSuchBoundedContext(id) =>
            NotFound(ErrorResponse(s"no such bounded context id: ${id.value}").json.play)
          case CreateDomainModelOutput.ConflictEnglishName(conflictedModel) =>
            Conflict(
              ErrorResponse(
                s"english name `${conflictedModel.englishName.value}` is conflicted in bounded context."
              ).json.play
            )
          case CreateDomainModelOutput.Success(newDomainModel) =>
            Created(DomainModelResponse(newDomainModel).json)
        }
      }
  }
}
```