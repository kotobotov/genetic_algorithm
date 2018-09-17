import org.scalatest.FreeSpec
import org.scalameter._

/**
  * Created by Kotobotov.ru on 17.09.2018.
  */
class Performance extends FreeSpec {
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

  "performance should be less than 50 sec" in {

    @volatile var parResult = false

    val standardConfig = config(
      Key.exec.minWarmupRuns -> 1,
      Key.exec.maxWarmupRuns -> 1,
      Key.exec.benchRuns -> 1,
      Key.verbose -> false
    ) withWarmer (new Warmer.Default)

    val geneBase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !?"
    val parTime = standardConfig measure {
      parResult = GeneticAlgorithm(geneBase, geneSize, fitness, mutationRate).maxScore >= 1
    }

    println(s"parallel result = $parResult")
    println(s"parallel evolution time: $parTime ms")
// usually i have 30 sec on 4 core 3.6Ghz maschine
    assert(parTime.value < 50000.0)

  }
}
