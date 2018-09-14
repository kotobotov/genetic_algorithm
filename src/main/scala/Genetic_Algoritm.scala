import scala.collection.parallel.immutable.ParSeq
import scala.util.Random

/**
  * Created by Kotobotov.ru on 22.07.2017.
  */
class Population(size: Int,
                 dnaSize: Int,
                 randomDNA: Int => DNA,
                 fitness: DNA => Double,
                 mutationRate: Int) {
  self =>
  var pool =
    (0 to size).map(item => randomDNA(dnaSize)).toParArray

  def chooseParent = {
    var sucseed = false

    def getSample = pool(Random.nextInt(pool.length - 1))

    var randomSample = getSample
    while (!sucseed) {
      if (randomSample.score > Random.nextFloat()) { sucseed = true } else
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
    self
  }

  var maxScore = 0.0

  def killWeak() = {
    pool = pool
      .filter(_.score > 0.0)
      .toArray
      .sortBy(-_.score)
      .take(1000)
      .par
    println(s"generation = ${pool.head.score} data ${pool.head.gene}")
    maxScore = pool.head.score
    self
  }
}

case class DNA(gene: String, score: Double = 0.0)

class GeneticAlgorithm() {

  val alpha =
    "abcdefghijklmnopqrstuvwxyz "
  val size = alpha.size
  val mutationRate = 1
  val target = "nujno nayti stroku dlay raboti"
  val dnaSize = target.size.toDouble

  def createPopulation() = {
    val MAX_POOL_SIZE = 1000000
    val recomendedSize =
      (if (math.pow(alpha.size * dnaSize, 2) > MAX_POOL_SIZE) MAX_POOL_SIZE
       else math.pow(alpha.size * dnaSize, 2)).toInt
    val myPool = new Population(recomendedSize, dnaSize.toInt, (_)=> DNA(""), (_)=>0.0, 3)
    myPool
  }

  def randomDNA(n: Int): DNA =
    DNA((1 to n).map(x => alpha(Random.nextInt(size))).mkString)

  def fitness(dna: DNA) = {
    dna.gene
      .zip(target)
      .filter(item => item._1 == item._2)
      .size
      .toDouble / dnaSize
  }

  def evalution() = {
    val population = new GeneticAlgorithm().createPopulation()
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

object GeneticAlgorithm {

  //(dnaBase: DNA, dnaSize: Int, mesure: DNA => Double)
  def apply(mutationRate: Int = 1) = new GeneticAlgorithm()
}

object Genetic_Algoritm extends App {

  val startMesureTime = System.currentTimeMillis()
  GeneticAlgorithm()
    .evalution()

  println("time: " + (System.currentTimeMillis() - startMesureTime))

}
