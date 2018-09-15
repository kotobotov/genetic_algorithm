import org.scalatest.FreeSpec
//import GeneticAlgorithm._

/**
  * Created by Kotobotov.ru on 13.09.2018.
  */
class GeneticTest extends FreeSpec {

  "Correctnes" - {
    "should have solution" in {
      val geneBase = "papapa"
      val geneSize = 10
     val target = "How many monkeys does it take to produce Shakespeare?"
      def fitnes(DNA: DNA) = 0.0
      val mutationRate = 3
      assert(
        new GeneticAlgorithm(geneBase, geneSize, fitnes, mutationRate)
          .runEvalution()
          .maxScore == 1)
    }

    "should have impruving" in {
      assert(Set.empty.size == 0)
    }

    "don't have solution" in {
      assertThrows[NoSuchElementException] {
        Set.empty.head
      }
    }
  }
}
