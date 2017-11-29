import org.scalatest._

import fakespeare.{HtmlParser, Play}

// feature: multiple trait usage
class HtmlParserTests extends FunSpec with Matchers with PlayLines {
  // feature: destructuring
  val Play(title, acts) = HtmlParser.parse(playLines)

  // feature: multiple parameter lists and call-by-name block usage
  // feature: various ways to define a testing DSL
  describe("The HtmlParser") {
    it("should remove html tags with the removeTags function") {
      HtmlParser.removeTags("<html><strong><h3>hello</strong></h3></html>") should be("hello")
    }

    it("should create three acts") {
      acts.size should be(3)
    }

    it("should have the expected title") {
      title should be("King Lear")
    }

    describe("the second act") {
      val secondAct = acts(1)

      it("should have a known title") {
        secondAct.title should be("ACT II")
      }

      describe("the first scene") {
        val ourScene = secondAct.scenes.head

        it("should have a known title") {
          ourScene.title should be("SCENE I. GLOUCESTER's castle.")
        }

        it("should have two speeches") {
          ourScene.speeches.size should be(2)
        }
      }

      describe("the second scene") {
        val ourScene = secondAct.scenes(1)

        it("should have tree speeches") {
          ourScene.speeches.size should be(3)
        }

        it("should have a known title") {
          ourScene.title should be("SCENE II. Before Gloucester's castle.")
        }

        it("should have a known setting") {
          ourScene.setting should be("Enter KENT and OSWALD, severally")
        }

        describe("the first speaker") {
          val secondSpeaker = ourScene.speeches.head

          it("should be Oswald speaking") {
            secondSpeaker.speaker should be("OSWALD")
          }

          it("should have a single line") {
            secondSpeaker.lines should be(List("Good dawning to thee, friend: art of this house?"))
          }
        }

        describe("the second speaker") {
          val secondSpeaker = ourScene.speeches(1)

          it("should be Kent speaking") {
            secondSpeaker.speaker should be("KENT")
          }

          it("should say 'Ay'") {
            secondSpeaker.words should be(List("Ay"))
          }
        }
      }
    }
  }
}
