
// feature: a simple trait to share test data + helper method
trait PlayLines {
  // feature: indented multi-line strings with stripMargin
  val playLines = // An excerpt (in malformed but predictable HTML)
    """<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
      | "http://www.w3.org/TR/REC-html40/loose.dtd">
      | <title>King Lear: Entire Play
      | <LINK rel="stylesheet" type="text/css" media="screen"
      |       href="/shake.css">
      |<tr><td class="play" align="center">King Lear
      |      <a href="/Shakespeare">Shakespeare homepage</A>
      |    | <A href="/lear/">King Lear</A>
      |    | Entire play
      |<H3>ACT I</h3>
      |<h3>SCENE I. King Lear's palace.</h3>
      |<i>Enter KENT, GLOUCESTER, and EDMUND</i>
      |<A NAME=speech1><b>KENT</b></a>
      |<A NAME=1.1.1>I thought the king had more affected the Duke of</A><br>
      |<A NAME=1.1.2>Albany than Cornwall.</A><br>
      |<H3>ACT II</h3>
      |<h3>SCENE I. GLOUCESTER's castle.</h3>
      |<i>Enter EDMUND, and CURAN meets him</i>
      |<A NAME=speech1><b>EDMUND</b></a>
      |<A NAME=2.1.1>Save thee, Curan.</A><br>
      |<A NAME=speech2><b>CURAN</b></a>
      |<A NAME=2.1.2>And you, sir. I have been with your father, and</A><br>
      |<A NAME=2.1.3>given him notice that the Duke of Cornwall and Regan</A><br>
      |<A NAME=2.1.4>his duchess will be here with him this night.</A><br>
      |<p><i>Exeunt</i></p>
      |<h3>SCENE II. Before Gloucester's castle.</h3>
      |<i>Enter KENT and OSWALD, severally</i>
      |<A NAME=speech1><b>OSWALD</b></a>
      |<A NAME=2.2.1>Good dawning to thee, friend: art of this house?</A><br>
      |<A NAME=speech2><b>KENT</b></a>
      |<A NAME=2.2.2>Ay.</A><br>
      |<A NAME=speech3><b>OSWALD</b></a>
      |<A NAME=2.2.3>Where may we set our horses?</A><br>
      |<H3>ACT III</h3>
      |<h3>SCENE I. A heath.</h3>
      |<i>Storm still. Enter KENT and a Gentleman, meeting</i>
      |<A NAME=speech1><b>KENT</b></a>
      |<A NAME=3.1.1>Who's there, besides foul weather?</A><br>
      |<A NAME=speech2><b>Gentleman</b></a>
      |<A NAME=3.1.2>One minded like the weather, most unquietly.</A><br>
    """.stripMargin.split("\n")

  // feature: _ as "don't care" (to avoid "declaration is never used")
  def inMemoryPlayLinesFetcher: String => Seq[String] = _ => playLines
}