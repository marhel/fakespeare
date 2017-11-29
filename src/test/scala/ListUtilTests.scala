import org.scalatest._

import fakespeare.ListUtil

class ListUtilTests extends FlatSpec with Matchers {
  // feature: lambdas are ordinary values
  val isEven = (x: Int) => (x % 2) == 0
  val header = (x: Char) => x == '<'

  // feature: various ways to define a testing DSL
  // feature: chain methods calls without dot notation

  "The split function" should "split on values matching the provided fun, keeping matching values" in {
    ListUtil.split("<--1--><--2--><--3-->".toList, header).map(_.mkString("")) should be(List("<--1-->", "<--2-->", "<--3-->"))
  }
  "The split function" should "not need a match" in {
    ListUtil.split(List(1, 3, 5, 7), isEven) should be(List(List(1, 3, 5, 7)))
  }
  "The split function" should "handle an empty list" in {
    ListUtil.split(List.empty[Int], isEven) should be(List.empty[Int])
  }
  "The split function" should "handle two consecutive separators" in {
    ListUtil.split(List(1, 1, 2, 4, 3, 5, 7), isEven) should be(List(List(1, 1), List(2), List(4, 3, 5, 7)))
  }
}
