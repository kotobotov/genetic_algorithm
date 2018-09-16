import org.scalatest.FreeSpec
import org.scalameter._

/**
  * Created by Kotobotov.ru on 13.09.2018.
  */
class AlgoTest extends FreeSpec {

  "Correctnes" - {

    val target = "How many monkeys does it take to produce Shakespeare?"
    val geneSize = target.length
    val mutationRate = 3
    def fitness = (dna: DNA) => {
      dna.gene
        .zip(target)
        .filter(item => item._1 == item._2)
        .size
        .toDouble / geneSize
    }

    "should have good solution" in {
      val geneBase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !?"
      assert(
        GeneticAlgorithm(geneBase, geneSize, fitness, mutationRate).maxScore >= 1)
    }

    "should have at least some solution" in {
      val geneBase = "abcdefghijk"
      assert(
        GeneticAlgorithm(geneBase, geneSize, fitness, mutationRate).maxScore > 0)
    }

    "should don't have any solution" in {
      val geneBase = "-1232342"
      assertThrows[Exception](
        GeneticAlgorithm(geneBase, geneSize, fitness, mutationRate))
    }

  }



}
