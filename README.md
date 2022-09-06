# Simple Domain Modeling Tool（SDMT）


## 概要

ドメインモデリングを支援するツールというテーマのプロジェクト。

要求定義やドキュメント、実装パターンなどを試すのが主目的です。


## 仕様など

- [Documents](https://rinotc.github.io/simple-domain-modeling-tool/)


## test coverage

[![Coverage Status](https://coveralls.io/repos/github/rinotc/simple-domain-modeling-tool/badge.svg?branch=main)](https://coveralls.io/github/rinotc/simple-domain-modeling-tool?branch=main)

[sbt-scoverage](https://github.com/scoverage/sbt-scoverage)

を利用している。

レポートの出力

```shell
sbt clean coverage test
sbt coverageAggregate
```
