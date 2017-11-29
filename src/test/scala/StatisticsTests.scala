import org.scalatest.{FunSpec, Matchers}

import fakespeare.Statistics

class StatisticsTests extends FunSpec with Matchers {
  val random = scala.util.Random
  val someWords = List("Two", "Two", "Eight", "Eight", "Eight", "Eight", "Eight", "Eight", "Eight", "Eight", "One", "Four", "Four", "Four", "Four")

  describe("the Statistics object") {
    it("can find the most frequent things from a freq map") {
      // feature: map (tuple) literals
      Statistics.top(3, Map("Two" -> 2, "Eight" -> 8, "One" -> 1, "Four" -> 4, "Zero" -> 0)) should be(List("Eight", "Four", "Two"))
    }
    it("can find the most frequent things from a list") {
      Statistics.top(3, someWords) should be(List("EIGHT", "FOUR", "TWO"))
    }
    it("can find frequency of things") {
      Statistics.freq(random.shuffle(someWords)) should be(Map("EIGHT" -> 8, "FOUR" -> 4, "TWO" -> 2, "ONE" -> 1))
    }
  }
}
