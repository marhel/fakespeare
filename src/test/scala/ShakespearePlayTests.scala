import org.scalatest.{FunSpec, Matchers}

import fakespeare.{ShakespearePlay, Statistics}

// Feature: trait usage
class ShakespearePlayTests extends FunSpec with Matchers with PlayLines {
  // feature: constructor dependency injection
  // feature: declare mutable with var, instead of val
  var play = new ShakespearePlay("dummy slug", inMemoryPlayLinesFetcher)

  describe("the ShakespearePlay class") {
    it("should contain three acts") {
      play.acts.size should be(3)
    }

    it("should contain four scenes") {
      play.scenes.size should be(4)
    }

    it("should contain eight speeches") {
      play.speeches.size should be(8)
    }

    it("should have five roles") {
      play.roles.size should be(5)
    }

    describe("should be able to safely find out if a particular scene exist") {
      // feature: Option usage instead of null
      it("should return Some when it does exist") {
        play.findSpeakerAt(2, 2, 2).map(_.speaker) should be(Some("KENT"))
      }
      it("should safely return None when act does not exist") {
        play.findSpeakerAt(99999, 2, 2).map(_.speaker) should be(None)
      }
      it("should safely return None when scene does not exist") {
        play.findSpeakerAt(2, 99999, 2).map(_.speaker) should be(None)
      }
      it("should safely return None when speech does not exist") {
        play.findSpeakerAt(2, 2, 99999).map(_.speaker) should be(None)
      }
    }


    it("should be possible to calculate top speaker") {
      // feature, import defs from a singleton object
      import Statistics._

      // feature: call-by-name blocks
      top(1) {
        val speeches = play.speeches
        val allSpeakers = speeches.map(_.speaker)
        freq(allSpeakers)
      } should be(List("KENT"))
    }
  }
}
