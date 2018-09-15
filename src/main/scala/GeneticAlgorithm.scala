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

  val target = "nujno nayti stroku dlay raboti"

  def createPopulation() = {
    val MAX_POOL_SIZE = 1000000
    val recomendedSize =
      (if (math.pow(alpha.size * dnaSize, 2) > MAX_POOL_SIZE) MAX_POOL_SIZE
       else math.pow(alpha.size * dnaSize, 2)).toInt
    val myPool =
      new Population(recomendedSize, dnaSize, randomDNA, fitnes, mutationRate)
    myPool
  }

  //def fitness(dna: DNA) = {
  //  dna.gene
  //    .zip(target)
  //    .filter(item => item._1 == item._2)
  //    .size
  //    .toDouble / dnaSize
  //}

  def runEvalution() = {
    val population = createPopulation()
    while (population.maxScore < 1.0) {
      println("best gene : " + population.pool.head.gene)
      population
        .evaluate()
        .killWeak()
        .makeNextGeneration()
    }
    population
  }

}

//object GeneticAlgorithm {

//(dnaBase: DNA, dnaSize: Int, mesure: DNA => Double)
//def apply(mutationRate: Int = 1) = new GeneticAlgorithm()
//}
