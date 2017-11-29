import fakespeare._
import Statistics._

val ado = ShakespearePlay.interpret("much_ado")
val speakers = ado.topSpeakers.map(_._1)
val stopWords = top(100, ado.words)
val topBySpeaker = speakers.map(name => (name, top(10, ado.freqBySpeaker(name) -- stopWords -- speakers))).toMap

topBySpeaker("BEATRICE")

ShakespearePlay.describe(ado.acts(2))
ShakespearePlay.describe(ado.acts(2).scenes(2))
ShakespearePlay.describe(ado.acts(2).scenes(2).speeches(4))

ado.generateFrom("I")
ado.generateFrom("THIS")

