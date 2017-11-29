import org.scalatest._

import fakespeare._

class PartsTests extends FlatSpec with Matchers {
  "The Speech class" should "be able to split lines into words with separators removed" in {
    Speech("hej", List("one: two", "three. four, five")).words should be(List("one", "two", "three", "four", "five"))
  }
}
