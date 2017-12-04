# Fakespeare

I was asked to hold a presentation on the Scala programming language, and came up with this demo. 

It is a parser of the full-text of [online Shakespeare plays](http://shakespeare.mit.edu/index.html) and a simple Markov chain based generator of fake Shakespeare quotes. Hence "Fakespeare".

At the moment, the only way to see the fake quotes is executing the src/Experiment.sc worksheet.

## Build, test and run

The project can be built, tested and run via gradle 4, using

    gradle build
    gradle run

The main method expects the first argument to be the stating word, and the rest of the arguments to be slugs for the plays to be loaded (see PlayFetcher.fetchOnline to work out the slugs for other plays).
The arguments defaults to the equivalent of:

    gradle run -PappArgs='["THIS", "hamlet", "much_ado", "macbeth", "lear", "midsummer", "othello"]'

Except that the start word is randomly picked from a list of common start words.

## Markov chains

A Markov chain is a data structure that can be created from a body of text,
where each element is a word, paired with a map of words that have been seen
following that word, and the frequency of each subsequent word.

In scala terms, the data structure backing a 2-word Markov chain could be a
`Map[String, Map[String, Int]]`, and the entry for "I", could be:

    I -> Map(HAD -> 71, KNOW -> 123, HAVE -> 289, WOULD -> 112, WILL -> 290, PRAY -> 84, AM -> 342, DO -> 158, THINK -> 78, MUST -> 65, ..., PRONOUNCE -> 1)

which means that in the analyzed text, we have seen the fragment "I HAD" 71
times, "I KNOW" 123 times, "I HAVE" 289 times and so on, while "I PRONOUNCE"
only occurs once.

This data structure can be easily walked to generate a sequence of rather high
quality, by picking a starting word ("I") and then randomly selecting the next
word based on the number of occurrences of that word relative to all other
words. In the analyzed text, there was 466 unique words following "I", and "I"
was followed by another word a total of 3394 times.

This means the probability of generating the sequence "I HAD" using this chain
is 71/3394 (about 2%), "I AM" is 342/3394 (about 10%) and "I PRONOUNCE" 1/3394
(about 0.3%). When we have selected the next word, we simply repeat the
process, using that word as an entry to find a word to follow that.

Let's say we picked "AM", then we fetch the entry for "AM", which is:

    AM -> Map(SORRY -> 12, NOT -> 28, GLAD -> 15, A -> 24, BOUND -> 7, SICK -> 7, NO -> 6, I -> 42, SURE -> 13, TO -> 9, ...)

This tells us that the most common three word phrase generated from this
chain, starting with "I" would be "I AM I", followed by "I AM NOT". Here we
can see the weakness of a chain of length two, because "I AM I" actually never
occurs in the analyzed text, and seems unlikely in most texts, but is happily
generated anyway.  This is because by the time we generate the third word, we
have lost the context of the first word, and "AM I" is of course quite
commonly seen, which results in this nonsense.

A Markov chain can be longer than two words, but the longer the chain, the
more text must be analyzed, or we will just reiterate entire chunks of source
text as we wouldn't see very many different words following, say, any given
7-word sequence, so for a chain of length 8, the generator would often be
forced to pick the only option available, which was the single usage found in
the analyzed text.

## Example Fakespeare sequence

The following are two sample 200-word runs based on a Markov chain built from 137963 words taken from the following plays:

* The Tragedy of Hamlet, Prince of Denmark (30508 words)
* Much Ado About Nothing (21189 words)
* The Tragedy of Macbeth (17035 words)
* King Lear (26178 words)
* A Midsummer Night's Dream (16565 words)
* Othello, the Moore of Venice (26488 words)

1) IT IS A FOREGONE CONCLUSION DUMBLY HAVE A MOST DISLOYAL THE WORLD HATH YOUR OWN DO IT TO HEAVEN I WILL UNDERTAKE FOR THEM WHY NO SUCH MAIMED RITES NOT YOU NAY BY'R OUR HONOUR'D AS GOD YOUR OFFER GO AND CALL UP AND WILL THROW TOAD THAT EVER THOU WHIPP'ST HER AFFECTIONS DESIRES HERE GIVE HER I AM ROSENCRANTZ GO SEEK THE PRIMROSE BEDS ARE INVISIBLE AND A FATHER REQUIRES A SCURVY AND THOROUGH THIS IGNORANT OF US SIGNIOR DO NOW GOOD LIEUTENANT CASSIO AS PRETTY LADY 'TIS HEAVY TERMS AGAINST THE MURMURING SURGE THAT HATH COMMANDED THANKS BRINGING HOME MY LORD LET NOT BUT O GOD I WOULD AY THOU TO RE ENTER ATTENDANT HATH INDEED IT THAT FELLOW AND IS OTHELLO'S VISAGE AS MY COUSIN BEATRICE TO LIVE FOR DINNER EXEUNT IT YOUR GRACE O' THE ANCIENT PRIVILEGE WHY DO YOU YOU BY MOONLIGHT AT MY LORD EDMUND SAY 'AY' AND WORSE THAN BLOODY DEED IN A VIRGIN CRANTS HER FATHER STOP NOT SO NO MY DEAR HAPPINESS THAT IT IN THE WATCH] BRING IN NIGHT 'TIS TRUE TELL US HENCE TO CALL HERS SIR MISTAKE AND MYSELF THOUGH BY NECESSITY OF HIS WIT O' THE UNTIMELY DEATH WERE

2) THIS WHOLE WORLD MUST EXPECT YOUR LOVE A BANK WILL WHAT IS BUT WHAT MAN IN THEIR NATURES OF KNOWN AND BLOOD HATH INDEED THE FOOLISH HONESTY MY HIPPOLYTA EGEUS AND MUST NOT BUT THIS RAIN TO THE CAULDRON BUBBLE COOL IT ON GOOD NOR SECURE I SHALL NOT BUT YOU ARE GONE WHO KILL'D HIS OWN DISGRACE AND HIS EYES AS GAMING THERE IF HE HATH MOVED THOUGH IN SOME VICIOUS BLOT THAT MOST ILL WELL DONE A VOTARESS PASSED BETWEEN THE SENSE IN PROCESSION THE THANES AND AIM THEREFORE SINCE WELL I AM BOUND TO ME GIVE THEM AND THEREFORE THIS IS HUSH'D WITHIN THERE IS I MUST GRANT SHE HIDE THEMSELVES WHAT YOU WAY TEND FOR THE GENTLE AND END FOR YOURSELF READ COMES THE FORMS TO THE WAY THOU LOOK LO WHERE IS PERSUADED TO GRACE MUST BE CORDS A TRUMPET CALLS TO LAUGHTER NEVER NEVER MAY BUT I HAVE POSITIVELY SAID THEY NOT HOW I SAY WELL WORTHY GENTLEMAN HOLDS IT BETTER DEATH HIS MESSAGE GIVE ME I HAVE A KING WHO STANDS STILL HIS MESSAGE GIVE HER EYES IN THEIR HOME MY LORD WHY THEN AND NIGHT THAT'S HONEST FELLOW THOU SHALT BE SAID I KNOW'T
