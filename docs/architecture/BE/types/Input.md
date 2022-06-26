# Input

`UseCase` の入力だ。普通に `case class` で実装してしまって良い。`Input` を継承する。

```scala
import dev.tchiba.arch.usecase.Input
import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}
import dev.tchiba.sdmt.usecase.boundedContext.create.CreateBoundedContextOutput

case class CreateBoundedContextInput(
  alias: BoundedContextAlias,
  name: BoundedContextName,
  overview: BoundedContextOverview
) extends Input[CreateBoundedContextOutput]
```