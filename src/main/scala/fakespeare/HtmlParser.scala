// feature: package name does not need to match folder structure
package fakespeare

// feature: singleton objects / static methods
object HtmlParser {
  // feature: implicit return type
  // feature: return a lambda function
  // feature: explicitly typed lambda parameter
  // feature: void/unit method callable without parens
  def whenLineContains(key: String) = (line: String) => line.toUpperCase.contains(key.toUpperCase)

  // feature: implicit return type
  // feature: generic function
  def splitAt[T](content: Seq[String], key: String, construct: List[String] => T): List[T] = ListUtil.split(content.toList, whenLineContains(key)).map(construct)

  def tagsOnly(line: String) = removeTags(line).length == 0

  def removeTags(line: String) = line.split("<.*?>").mkString(" ").trim

  // feature: explicit return type
  def parse(lines: Seq[String]): Play = splitAt(lines, "class=\"play\"", createPlay).drop(1).head

  // feature: last evaluated expression is the return value
  def createPlay(lines: List[String]): Play = {
    val title = removeTags(lines.head)
    val acts = splitAt(lines, "<H3>ACT", createAct).drop(1)
    Play(title, acts)
  }

  def createAct(lines: List[String]): Act = {
    val title = removeTags(lines.head)
    val content = lines.drop(1)
    val scenes = splitAt(content, "<H3>SCENE", createScene)
    Act(title, scenes)
  }

  def createScene(lines: List[String]): Scene = {
    val title = removeTags(lines.head)
    val setting: String = removeTags(lines.drop(1).head)
    val content = lines.drop(2)
    val speeches = splitAt(content, "NAME=SPEECH", createSpeech)
    Scene(title, setting, speeches)
  }

  def createSpeech(lines: List[String]): Speech = {
    val speaker = removeTags(lines.head)
    val speechLines = lines.drop(1).map(removeTags)
    Speech(speaker, speechLines)
  }
}
