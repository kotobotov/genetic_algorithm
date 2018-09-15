import scala.util.Random

/**
  * Created by Kotobotov.ru on 14.09.2018.
  */
class Population(size: Int,
                 dnaSize: Int,
                 randomDNA: Int => DNA,
                 fitness: DNA => Double,
                 mutationRate: Int) {
  self =>
  var maxScore = 0.0
var generation = 0
  var pool = createPool

  def createPool = (0 to size).map(_ => randomDNA(dnaSize)).toParArray
  def chooseParent = {
// important! : can only choose DNA with more than 0 score
    var succeed = false

    def getSample = pool(Random.nextInt(pool.length - 1))
    var randomSample = getSample
    while (!succeed) {
      if (randomSample.score > Random.nextFloat()) { succeed = true } else
        randomSample = getSample
    }
    randomSample
  }

  def crosover(mather: DNA): DNA = {
    def mutate(input: DNA) = {
      input.gene.toCharArray
        .map(
          item =>
            if (mutationRate > Random.nextInt(100)) {
              randomDNA(1).gene.head
            } else item
        )
        .mkString
      input
    }
    val father = chooseParent
    val spliter = Random.nextInt(dnaSize)
    val child = DNA(
      mather.gene.substring(0, spliter) +
        father.gene.substring(spliter, dnaSize))
    mutate(child)
  }

  def makeNextGeneration() = {
    var newPool = pool.map(dna => crosover(dna))
    while (newPool.size < 3000) {
      newPool ++= pool.map(dna => crosover(dna))
    }
    pool = newPool
    self
  }

  def evaluate() = {
    pool = pool.map { dna =>
      dna.copy(score = fitness(dna))
    }
    generation +=1
    self
  }

  def killWeak() = {
    pool = pool
      .filter(_.score > 0.0)
      .toArray
      .sortBy(-_.score)
      .take(1000)
      .par
    maxScore = pool.head.score
    println(s"generation: $generation score: $maxScore gene: ${pool.head.gene}")
    self
  }
}
