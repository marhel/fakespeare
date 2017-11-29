package fakespeare

object Statistics {

  def top(n: Int, m: List[String]): List[String] = top(n, freq(m))

  // feature: method overloading
  def top(n: Int, m: Map[String, Int]): List[String] = m.toList.sortBy(_._2).reverse.take(n).map(_._1)

  // feature: call-by-name param
  // feature: multiple parameter lists
  def top(n: Int)(gen: => Map[String, Int]): List[String] = gen.toList.sortBy(_._2).reverse.take(n).map(_._1)

  def toChain(words: List[String]): MarkovChain = new MarkovChain(
    // feature: excessivly long expressions are safe, though maybe not extremely readable
    words.sliding(2).toList
      .map(w => (w.head, w.last))
      .groupBy(_._1.toUpperCase())
      .mapValues(v => freq(v.map(_._2))))

  def freq(words: List[String]) = words.groupBy(_.toUpperCase()).mapValues(_.length)
}

class MarkovChain(val chain: Map[String, Map[String, Int]]) {
  val random = new scala.util.Random

  // feature: returns Option instead of null
  def pickWord(probs: Map[String, Int]): Option[String] = {
    val propCounts = probs.values.sum
    var remainingCount = if (propCounts > 0) random.nextInt(propCounts) else 0
    // feature: ordinary for-loop (less common in Scala due to FP)
    for (prob <- probs) {
      remainingCount -= prob._2
      if (remainingCount <= 0) {
        // feature: explicit early return
        return Some(prob._1)
      }
    }
    None
  }

  // feature: recursion
  // feature: default parameter value
  def walk(word: String, depth: Int = 15): String = {
    chain.get(word).flatMap(pickWord).map { nextWord =>
      if (depth > 0)
        word + " " + walk(nextWord, depth - 1)
      else
        word + " " + nextWord
    // feature: fallback to default value with getOrElse
    }.getOrElse(word)
  }
}

