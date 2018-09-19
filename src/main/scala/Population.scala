import scala.util.Random

/**
  * Created by Kotobotov.ru on 14.09.2018.
  */
class Population
  (recommendPoolSize: Int,
                 dnaSize: Int,
                 randomDNA: Int => DNA,
                 fitness: DNA => Double,
                 mutationRate: Int) {
  self =>
  var maxScore = 0.0
  var currentGeneration = 0
  private val recommendSurviveSize = (recommendPoolSize*0.1).toInt
  var pool = createPool

  def createPool = (0 to recommendPoolSize).map(_ => randomDNA(dnaSize)).toParArray.distinct
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

  def crossover(mather: DNA): DNA = {
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
    val splitter = Random.nextInt(dnaSize)
    val child = DNA(
      mather.gene.substring(0, splitter) +
        father.gene.substring(splitter, dnaSize))
    mutate(child)
  }

  def makeNextGeneration() = {
    var newPool = pool.map(dna => crossover(dna))
    while (newPool.size < recommendPoolSize) {
      newPool ++= pool.map(dna => crossover(dna))
    }
    pool = newPool.distinct
    self
  }

  def evaluate() = {
    pool = pool.map { dna =>
      dna.copy(score = fitness(dna))
    }
    currentGeneration += 1
    self
  }

  def killWeak() = {
    pool = pool
      .filter(_.score > 0.0)
      .toArray
      .sortBy(-_.score)
      .take(recommendSurviveSize)
      .par
    //maxScore = pool.headOption.getOrElse(DNA("", 0.0)).score
    maxScore = pool.head.score
    println(s"generation: $currentGeneration score: $maxScore gene: ${pool.headOption.getOrElse(DNA("NO GENE FOUND", 0.0)).gene}")
    self
  }
}