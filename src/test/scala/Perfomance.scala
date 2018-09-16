import org.scalatest.FreeSpec
import org.scalameter._

/**
  * Created by Kotobotov.ru on 17.09.2018.
  */
class Perfomance extends FreeSpec {
  val target = "How many monkeys does it take to produce Shakespeare?"
  val geneSize = target.length
  val mutationRate = 3
  def fitness = (dna: DNA) => {
    Thread.sleep(1)
    dna.gene
      .zip(target)
      .filter(item => item._1 == item._2)
      .size
      .toDouble / geneSize
  }

  "with 10 mills/evaluation simulation" in {

    @volatile var seqResult = false

    @volatile var parResult = false

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 1,
      Key.exec.maxWarmupRuns -> 1,
      Key.exec.benchRuns -> 1,
      Key.verbose -> false
    ) withWarmer (new Warmer.Default)

    val geneBase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !?"
    val seqtime = standardConfig measure {
      seqResult = GeneticAlgorithm(geneBase, geneSize, fitness, mutationRate).maxScore == 1
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    //val fjtime = standardConfig measure {
    //  parResult = GeneticAlgorithm(geneBase, geneSize, fitnes, mutationRate).maxScore == 1
    //}
    //println(s"parallel result = $parResult")
    //println(s"parallel balancing time: $fjtime ms")
    //println(s"speedup: ${seqtime / fjtime}")

  }
}
