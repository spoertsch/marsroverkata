import org.scalatest._

import scala.math.Ordered.orderingToOrdered

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
    marsRover.getGrid() shouldBe (100, 100)
  }

  it should "have a default starting point" in new MarsRoverFixture {
    marsRover.getStartingPoint() shouldBe (0, 0)
  }

  it should "have a non default starting point" in {
    val marsRover = new MarsRover(Grid(), 1, 1)
    marsRover.getStartingPoint() shouldBe (1, 1)
  }

  it should "not allow a negative x starting point" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), -1, 1)
    }
  }

  it should "not allow a negative y starting point" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), 1, -1)
    }
  }

  it should "not allow a starting point x outside the defined grid" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), 100, 0)
    }
  }

  it should "not allow a starting point y outside the defined grid" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), 0, 100)
    }
  }

  it should "have a default direction (N)" in new MarsRoverFixture {
    marsRover.getDirection() shouldBe North()
  }

  it should "take S as direction" in {
    val marsRover = new MarsRover(Grid(), 0, 0, South())
    marsRover.getDirection() shouldBe South()
  }


}