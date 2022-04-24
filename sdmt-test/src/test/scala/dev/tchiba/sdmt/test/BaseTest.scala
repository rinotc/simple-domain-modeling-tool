package dev.tchiba.sdmt.test

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{EitherValues, OptionValues}

trait BaseTest extends AnyWordSpec with Matchers with OptionValues with EitherValues
