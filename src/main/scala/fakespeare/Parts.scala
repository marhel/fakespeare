// feature: package name does not need to match folder structure
package fakespeare

// feature: trait, zero boilerplate
sealed trait Part

// feature: case classes, zero boilerplate
case class Play(title: String, acts: List[Act]) extends Part

case class Act(title: String, scenes: List[Scene]) extends Part

case class Scene(title: String, setting: String, speeches: List[Speech]) extends Part

case class Speech(speaker: String, lines: List[String]) extends Part {
  def words = lines.flatMap(_.split("[\\s!?\\.,:;-]")).filterNot(_.isEmpty)
}

