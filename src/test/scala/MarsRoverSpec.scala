import org.scalatest._

/**
 * Test class for MarsRover
 *
 * Created by jst on 24.09.14.
 */
class MarsRoverSpec extends FlatSpec with Matchers {

  trait MarsRoverFixture {
    val marsRover = new MarsRover()
  }

  "A MarsRover" should "be created" in new MarsRoverFixture {
    marsRover should not be Nil
  }

  it should "have a grid" in new MarsRoverFixture {
    marsRover.getGrid() shouldBe (100,100)
  }

}