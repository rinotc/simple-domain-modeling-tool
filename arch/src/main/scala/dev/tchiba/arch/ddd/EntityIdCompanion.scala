package dev.tchiba.arch.ddd

trait EntityIdCompanion[U, E <: EntityId[U]] {

  type ValidateValueType

  def apply(value: U): E

  def validate(value: ValidateValueType): Either[String, E]

  def generate(): E
}
