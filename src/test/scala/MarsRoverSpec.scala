import org.scalatest._

/**
 * Test class for MarsRover
 *
 * Created by jst on 24.09.14.
 */
class MarsRoverSpec extends FlatSpec with Matchers {

  trait MarsRoverFixture {
    val marsRover = new MarsRover(Grid())
  }

  "A MarsRover" should "be created" in new MarsRoverFixture {
    marsRover should not be Nil
  }

  it should "have a default grid" in new MarsRoverFixture {
    marsRover.getGrid() shouldBe (100,100)
  }

  it should "have a default starting point" in new MarsRoverFixture {
    marsRover.getStartingPoint() shouldBe (0,0)
  }

  it should "have a non default starting point" in {
    val marsRover = new MarsRover(Grid(), 1,1)
    marsRover.getStartingPoint() shouldBe (1,1)
  }

}