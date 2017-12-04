package fakespeare

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths, StandardOpenOption}

import scala.collection.JavaConverters._

object PlayFetcher {

  import HtmlParser._

  def read(playSlug: String): List[String] = {
    val cacheFilePath = Paths.get("plays", s"$playSlug.play.txt")

    // feature: last evaluated expression is the return value (if-expression)
    if (Files.exists(cacheFilePath)) {
      Files.readAllLines(cacheFilePath).asScala.toList
    } else {
      // feature: nested method definition
      def writeToCache(lines: List[String], cacheFilePath: Path) = {
        Files.write(cacheFilePath, lines.mkString("\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW)
      }

      val lines: List[String] = fetchOnline(playSlug)
      writeToCache(lines, cacheFilePath)
      lines
    }
  }

  def fetchOnline(playSlug: String) = {
    // feature: string interpolation
    // See http://shakespeare.mit.edu/index.html for more plays
    val playUrl = s"http://shakespeare.mit.edu/$playSlug/full.html"
    io.Source.fromURL(playUrl).getLines().toList
      .filterNot(tagsOnly)
      .filterNot(_.trim.isEmpty)
  }
}


