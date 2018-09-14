import org.scalatest.FreeSpec

/**
  * Created by Kotobotov.ru on 13.09.2018.
  */
class GeneticTest extends FreeSpec {

  "Correctnes" - {
    "should have solution" in {
      assert(Set.empty.size == 0)
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
