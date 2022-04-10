# ScalikeJDBC コード自動生成

- [ScalikeJDBC コード自動生成](https://seratch.gitbooks.io/scalikejdbc-cookbook/content/ja/09_source_code_generator.html)

`scalikejdbc-mapper-generator` を使って、DBのスキーマからソースコードを自動生成する。

## やり方

```shell
# Usage
sbt scalikejdbcGen [table-name (class-name)]

# 例
sbt sdmt-infra-scalikejdbc/scalikejdbcGen users 
```
