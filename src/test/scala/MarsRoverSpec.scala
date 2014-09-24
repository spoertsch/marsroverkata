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
    marsRover.grid.getAsTuple() shouldBe (100, 100)
  }

  it should "have a default starting point" in new MarsRoverFixture {
    marsRover.startPosition.getAsTuple() shouldBe (0, 0)
  }

  it should "have a non default starting point" in {
    val marsRover = new MarsRover(Grid(), Position(1, 1))
    marsRover.startPosition.getAsTuple() shouldBe (1, 1)
  }

  it should "not allow a negative x starting point" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), Position(-1, 1))
    }
  }

  it should "not allow a negative y starting point" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), Position(1, -1))
    }
  }

  it should "not allow a starting point x outside the defined grid" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), Position(100, 0))
    }
  }

  it should "not allow a starting point y outside the defined grid" in {
    a[IllegalArgumentException] should be thrownBy {
      new MarsRover(Grid(), Position(0, 100))
    }
  }

  it should "have a default direction (N)" in new MarsRoverFixture {
    marsRover.direction shouldBe North()
  }

  it should "take S as direction" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), South())
    marsRover.direction shouldBe South()
  }

  it should "return the current position" in new MarsRoverFixture {
    marsRover.getCurrentPosition().getAsTuple() shouldBe (0,0)
  }

  // Default start pos (0,0) facing North, moving forward should move to (0,1)
  it should "move forward" in new MarsRoverFixture {
    val newPos = marsRover.move(Array('f'))
    newPos.getAsTuple() shouldBe (0, 1)
  }
}