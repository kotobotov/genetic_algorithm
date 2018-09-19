import scala.util.Random

/**
  * Created by Kotobotov.ru on 14.09.2018.
  */
class GeneticAlgorithm(alpha: String,
                       dnaSize: Int,
                       fitness: DNA => Double,
                       mutationRate: Int) {

  val size = alpha.size
  def randomDNA =
    (n: Int) => DNA((1 to n).map(x => alpha(Random.nextInt(size))).mkString)

  def createPopulation() = {
    val MAX_POOL_SIZE = 100000
    println(alpha.size * dnaSize * 2 + " " + alpha.size + " " +dnaSize )
    val recommendedSize =
      if (alpha.size * dnaSize > MAX_POOL_SIZE * 2) MAX_POOL_SIZE
       else alpha.size * dnaSize * 2
    val myPool =
      new Population(recommendedSize, dnaSize, randomDNA, fitness, mutationRate)
    myPool
  }

  def runEvalution() = {
    var notHaveEvalution = 0
    val MAX_GENERATION_WITHOUT_EVALUTION = 4
    var lastMaxScore = 0.0
    val population = createPopulation()

    def isGoodEnouth: Boolean = {
      if (lastMaxScore < population.maxScore) {
        notHaveEvalution = 0
        lastMaxScore = population.maxScore
      } else { notHaveEvalution += 1 }
      notHaveEvalution > MAX_GENERATION_WITHOUT_EVALUTION
    }

    while (!isGoodEnouth) {
      population
        .evaluate()
        .killWeak()
        .makeNextGeneration()
    }
    population
  }
}

object GeneticAlgorithm {

  /**
    * @param geneBase - baze whitch is used to generate Genes
    * @param geneSize - numbers element of resulting Gene
    * @param fitness - your evaluating function from input DNA to Double
    * @param mutationRate - percent of random mutation (from 1 to 100) [optional]
    * @return Population class with pool of DNA
    *
    */
  def apply(alpha: String,
            dnaSize: Int,
            fitness: DNA => Double,
            mutationRate: Int = 3) =
    new GeneticAlgorithm(alpha, dnaSize, fitness, mutationRate).runEvalution()
}
