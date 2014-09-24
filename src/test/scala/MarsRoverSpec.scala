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

  // Default start pos (0,0) facing North, moving forward should move to (0,1) but not change direction
  it should "move forward" in new MarsRoverFixture {
    val newPos = marsRover.move(Array('f'))
    newPos.getAsTuple() shouldBe (0, 1)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "not move on an invalid character (only f,b,r,l allowed)" in new MarsRoverFixture {
    val newPos = marsRover.move(Array('x'))
    newPos.getAsTuple() shouldBe (0, 0)
  }

  // Start pos (0,1) facing North, moving backward should move to (0,0) but not change direction
  it should "move backward" in {
    val marsRover = new MarsRover(Grid(), Position(0,1))
    val newPos = marsRover.move(Array('b'))
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "return the current direction" in new MarsRoverFixture {
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "not change direction on an invalid character (only f,b,r,l allowed)" in new MarsRoverFixture {
    marsRover.move(Array('x'))
    marsRover.getCurrentDirection() shouldBe North()
  }

  // Default start pos (0,0) facing North, moving 'ffbf' should move to (0, 2) but not change direction
  it should "handle multiple moving commands" in new MarsRoverFixture {
    val newPos = marsRover.move(Array('f', 'f', 'b', 'f'))
    newPos.getAsTuple() shouldBe (0, 2)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "handle multiple moving commands (as String)" in new MarsRoverFixture {
    val newPos = marsRover.move("ffbf")
    newPos.getAsTuple() shouldBe (0, 2)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "turn left from default position" in new MarsRoverFixture {
    val newPos = marsRover.move("l")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe West()
  }

  it should "turn left (from North to West)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), North())
    val newPos = marsRover.move("l")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe West()
  }

  it should "turn left (from West to South)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), West())
    val newPos = marsRover.move("l")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe South()
  }

  it should "turn left (from South to East)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), South())
    val newPos = marsRover.move("l")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe East()
  }

  it should "turn left (from East to North)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), East())
    val newPos = marsRover.move("l")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "turn right from default position" in new MarsRoverFixture {
    val newPos = marsRover.move("r")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe East()
  }

  it should "turn right (from North to East)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), North())
    val newPos = marsRover.move("r")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe East()
  }

  it should "turn right (from East to South)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), East())
    val newPos = marsRover.move("r")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe South()
  }

  it should "turn right (from South to West)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), South())
    val newPos = marsRover.move("r")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe West()
  }

  it should "turn right (from West to North)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), West())
    val newPos = marsRover.move("r")
    newPos.getAsTuple() shouldBe (0, 0)
    marsRover.getCurrentDirection() shouldBe North()
  }

  // North -> West -> South -> West -> South -> East
  it should "handle multiple turns" in new MarsRoverFixture {
    val newPos = marsRover.move("llrll")
    newPos.getAsTuple() shouldBe (0,0)
    marsRover.getCurrentDirection() shouldBe East()
  }

  // (0,0)N -> (0,1)N -> (0,1)E -> (1,1)E -> (2,1)E -> (2,1)N -> (2,0)N
  it should "handle mixed commands (turns and movements)" in new MarsRoverFixture {
    val newPos = marsRover.move("frfflb")
    newPos.getAsTuple() shouldBe (2,0)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "move correctly move forward when looking North (y + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), North())
    val newPos = marsRover.move("f")
    newPos.getAsTuple() shouldBe (0, 1)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "move correctly move forward when looking South (y - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,5), South())
    val newPos = marsRover.move("f")
    newPos.getAsTuple() shouldBe (0, 4)
    marsRover.getCurrentDirection() shouldBe South()
  }

  it should "move correctly move forward when looking East (x + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), East())
    val newPos = marsRover.move("f")
    newPos.getAsTuple() shouldBe (1, 0)
    marsRover.getCurrentDirection() shouldBe East()
  }

  it should "move correctly move forward when looking West (x - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(5,0), West())
    val newPos = marsRover.move("f")
    newPos.getAsTuple() shouldBe (4, 0)
    marsRover.getCurrentDirection() shouldBe West()
  }

  it should "move correctly move backward when looking North (y - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,5), North())
    val newPos = marsRover.move("b")
    newPos.getAsTuple() shouldBe (0, 4)
    marsRover.getCurrentDirection() shouldBe North()
  }

  it should "move correctly move backward when looking South (y + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), South())
    val newPos = marsRover.move("b")
    newPos.getAsTuple() shouldBe (0, 1)
    marsRover.getCurrentDirection() shouldBe South()
  }

  it should "move correctly move backward when looking East (x - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(5,0), East())
    val newPos = marsRover.move("b")
    newPos.getAsTuple() shouldBe (4, 0)
    marsRover.getCurrentDirection() shouldBe East()
  }

  it should "move correctly move backward when looking West (x + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(5,0), West())
    val newPos = marsRover.move("b")
    newPos.getAsTuple() shouldBe (6, 0)
    marsRover.getCurrentDirection() shouldBe West()
  }
}