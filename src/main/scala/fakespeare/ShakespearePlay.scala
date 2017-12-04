package fakespeare

import fakespeare.Statistics.toChain

// feature: singleton object / static methods
// feature: companion object
object ShakespearePlay {
  // See http://shakespeare.mit.edu/index.html for more plays
  val well_known = List("hamlet", "macbeth", "midsummer", "much_ado", "lear", "othello")

  def interpret(play: String) = new ShakespearePlay(play)

  def describe(part: Part): String = {
    // feature: pattern matching
    // feature: destructuring cases
    // feature: match may not be exhaustive
    // feature: _ as "don't care" (to avoid "declaration is never used")
    part match {
      case Play(title, _) => title
      case Act(title, scenes) =>
        s"""$title
           |${scenes.map(describe).mkString("\n")})
           |""".stripMargin
      case Scene(title, setting, _) => s"$title\n$setting"
      case Speech(speaker, lines) => s"""$speaker: ${lines.take(10).mkString("\n   ")}"""
      case _ => "Unknown part " + part.getClass.getSimpleName
    }
  }

  def extractWords(shakespearePlay: ShakespearePlay): List[String] = {
    print(describe(shakespearePlay.play))
    println(" (%s words)".format(shakespearePlay.words.size))
    shakespearePlay.words
  }

  def process(word: String, plays: Seq[String]) = {
    val allWords = plays.map(interpret).flatMap(extractWords)
    val chain = toChain(allWords)
    println
    println("Generating Fakespeare sequence based on %s words".format(allWords.size))
    println
    println(chain.walk(word, 200))
  }

  def main(args: Array[String]): Unit = {
    println("Fakespeare")
    args.toList match {
      case word :: plays => process(word, plays)
      case _ => process("THIS", ShakespearePlay.well_known)
    }
  }
}

// feature: ordinary class, with constructor arguments, HOF with default parameter value
class ShakespearePlay(val playSlug: String, fetcher: String => Seq[String] = PlayFetcher.read) {
  // feature: the body of the class _is_ the constructor

  // feature: scoped imports, nesting
  import Statistics._
  import ShakespearePlay._

  // feature: type inference
  // feature: immutability
  val play = HtmlParser.parse(fetcher(playSlug))
  val acts = play.acts
  val scenes = acts.flatMap(_.scenes)
  val speeches = acts.flatMap(_.scenes.flatMap(_.speeches))
  val roles = acts.flatMap(_.scenes.flatMap(_.speeches)).map(_.speaker).toSet
  val words = acts.flatMap(_.scenes.flatMap(_.speeches.flatMap(_.words)))

  // feature: underscore as lambda param shortcut
  // feature: explicitly named lambda param
  // feature: tuple usage
  val topSpeakers = speeches
    .groupBy(_.speaker)
    .mapValues(speech => speech.map(_.words.size).sum)
    .toList.sortBy(g => g._2)
    .reverse
  val freqBySpeaker = speeches
    .groupBy(_.speaker)
    .mapValues(speech => freq(speech.flatMap(_.words)))
  val chain = toChain(words)

  // feature: default parameter value
  def generateFrom(word: String, length: Int = 15) = chain.walk(word, length)

  def findSpeakerAt(act: Int, scene: Int, speech: Int): Option[Speech] = {
    // feature: chain methods calls without dot notation
    // feature: Option usage instead of null
    acts.lift(act - 1) flatMap { act =>
      act.scenes.lift(scene - 1) flatMap { scene =>
        scene.speeches.lift(speech - 1)
      }
    }
  }

  // feature: override method (from Object)
  override def toString: String = describe(play)
}