import scala.util.Random

/**
  * Created by Kotobotov.ru on 14.09.2018.
  */
class GeneticAlgorithm(alpha: String,
                       dnaSize: Int,
                       fitnes: DNA => Double,
                       mutationRate: Int) {

  val size = alpha.size

  def randomDNA = (n:Int)=>
    DNA((1 to n).map(x => alpha(Random.nextInt(size))).mkString)

  def createPopulation() = {
    val MAX_POOL_SIZE = 1000000
    val recomendedSize =
      (if (math.pow(alpha.size * dnaSize, 2) > MAX_POOL_SIZE) MAX_POOL_SIZE
       else math.pow(alpha.size * dnaSize, 2)).toInt
    val myPool =
      new Population(recomendedSize, dnaSize, randomDNA, fitnes, mutationRate)
    myPool
  }

  def runEvalution() = {
    val MAX_GENERATION_WITHOUT_EVALUTION=4
    var notHaveEvalution = 0
    val population = createPopulation()
    while (notHaveEvalution < MAX_GENERATION_WITHOUT_EVALUTION) {
      population
        .evaluate()
        .killWeak()
        .makeNextGeneration()
      if (population.lastMaxScore < population.maxScore) notHaveEvalution = 0 else notHaveEvalution+=1
    }
    population
  }

}

//object GeneticAlgorithm {

//(dnaBase: DNA, dnaSize: Int, mesure: DNA => Double)
//def apply(mutationRate: Int = 1) = new GeneticAlgorithm()
//}
