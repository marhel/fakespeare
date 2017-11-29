package fakespeare

object ListUtil {
  // feature: generic function
  // feature: higher order functions
  // feature: last evaluated expression is the return value
  def split[T](input: List[T], isSeparator: T => Boolean, results: List[List[T]] = Nil): List[List[T]] = {
    if (input.isEmpty)
      results.reverse
    else {
      // feature: destructuring / tuple usage
      val (l, r) = input.zipWithIndex.span(c => c._2 == 0 || !isSeparator(c._1))
      // feature: tail recursion
      split(r.map(_._1), isSeparator, l.map(_._1) :: results)
    }
  }
}
