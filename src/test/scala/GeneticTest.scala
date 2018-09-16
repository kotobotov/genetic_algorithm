import org.scalatest.FreeSpec
//import GeneticAlgorithm._

/**
  * Created by Kotobotov.ru on 13.09.2018.
  */
class GeneticTest extends FreeSpec {

  "Correctnes" - {
    "should have good solution" in {
      val geneBase = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !?"
      val target = "How many monkeys does it take to produce Shakespeare?"
      val geneSize = target.length
      def fitnes = (dna: DNA) => {
        dna.gene
          .zip(target)
          .filter(item => item._1 == item._2)
          .size
          .toDouble / geneSize
      }
      val mutationRate = 3
      assert(
        new GeneticAlgorithm(geneBase, geneSize, fitnes, mutationRate)
          .runEvalution()
          .maxScore == 1)
    }

    "should have at least some solution" in {
      val geneBase = "abcdefghijk"
      val target = "How many monkeys does it take to produce Shakespeare?"
      val geneSize = target.length
      def fitnes = (dna: DNA) => {
        dna.gene
        .zip(target)
        .filter(item => item._1 == item._2)
        .size
        .toDouble / geneSize
      }
      val mutationRate = 3
      assert(
        new GeneticAlgorithm(geneBase, geneSize, fitnes, mutationRate)
          .runEvalution()
          .maxScore > 0)
    }

    "should don't have any solution" in {
      val geneBase = "-1232342"
      val target = "How many monkeys does it take to produce Shakespeare?"
      val geneSize = target.length
      def fitnes = (dna: DNA) => {
        dna.gene
        .zip(target)
        .filter(item => item._1 == item._2)
        .size
        .toDouble / geneSize
      }
      val mutationRate = 3
      assertThrows[Exception](
        new GeneticAlgorithm(geneBase, geneSize, fitnes, mutationRate)
          .runEvalution()
          .maxScore)
    }

  }
}
