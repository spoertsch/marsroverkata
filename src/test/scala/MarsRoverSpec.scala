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
    marsRover.grid shouldBe Grid(100, 100)
  }

  it should "have a default starting point" in new MarsRoverFixture {
    marsRover.startPosition shouldBe Position(0, 0)
  }

  it should "have a non default starting point" in {
    val marsRover = new MarsRover(Grid(), Position(1, 1))
    marsRover.startPosition shouldBe Position(1, 1)
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
    marsRover.getCurrentPosition() shouldBe Position(0,0)
  }

  // Default start pos (0,0) facing North, moving forward should move to (0,1) but not change direction
  it should "move forward" in new MarsRoverFixture {
    val newPos = marsRover.move(Array('f'))
    checkPositionAndDirection(marsRover, newPos, Position(0, 1), North())
  }

  it should "not move on an invalid character (only f,b,r,l allowed)" in new MarsRoverFixture {
    val newPos = marsRover.move(Array('x'))
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), North())
  }

  // Start pos (0,1) facing North, moving backward should move to (0,0) but not change direction
  it should "move backward" in {
    val marsRover = new MarsRover(Grid(), Position(0,1))
    val newPos = marsRover.move(Array('b'))
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), North())
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
    checkPositionAndDirection(marsRover, newPos, Position(0, 2), North())
  }

  it should "handle multiple moving commands (as String)" in new MarsRoverFixture {
    val newPos = marsRover.move("ffbf")
    checkPositionAndDirection(marsRover, newPos, Position(0, 2), North())
  }

  it should "turn left from default position" in new MarsRoverFixture {
    val newPos = marsRover.move("l")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), West())
  }

  it should "turn left (from North to West)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), North())
    val newPos = marsRover.move("l")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), West())
  }

  it should "turn left (from West to South)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), West())
    val newPos = marsRover.move("l")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), South())
  }

  it should "turn left (from South to East)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), South())
    val newPos = marsRover.move("l")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), East())
  }

  it should "turn left (from East to North)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), East())
    val newPos = marsRover.move("l")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), North())
  }

  it should "turn right from default position" in new MarsRoverFixture {
    val newPos = marsRover.move("r")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), East())
  }

  it should "turn right (from North to East)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), North())
    val newPos = marsRover.move("r")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), East())
  }

  it should "turn right (from East to South)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), East())
    val newPos = marsRover.move("r")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), South())
  }

  it should "turn right (from South to West)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), South())
    val newPos = marsRover.move("r")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), West())
  }

  it should "turn right (from West to North)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), West())
    val newPos = marsRover.move("r")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), North())
  }

  // North -> West -> South -> West -> South -> East
  it should "handle multiple turns" in new MarsRoverFixture {
    val newPos = marsRover.move("llrll")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), East())
  }

  // (0,0)N -> (0,1)N -> (0,1)E -> (1,1)E -> (2,1)E -> (2,1)N -> (2,0)N
  it should "handle mixed commands (turns and movements)" in new MarsRoverFixture {
    val newPos = marsRover.move("frfflb")
    checkPositionAndDirection(marsRover, newPos, Position(2, 0), North())
  }

  it should "move correctly move forward when looking North (y + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), North())
    val newPos = marsRover.move("f")
    checkPositionAndDirection(marsRover, newPos, Position(0, 1), North())
  }

  it should "move correctly move forward when looking South (y - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,5), South())
    val newPos = marsRover.move("f")
    checkPositionAndDirection(marsRover, newPos, Position(0, 4), South())
  }

  it should "move correctly move forward when looking East (x + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), East())
    val newPos = marsRover.move("f")
    checkPositionAndDirection(marsRover, newPos, Position(1, 0), East())
  }

  it should "move correctly move forward when looking West (x - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(5,0), West())
    val newPos = marsRover.move("f")
    checkPositionAndDirection(marsRover, newPos, Position(4, 0), West())
  }

  it should "move correctly move backward when looking North (y - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,5), North())
    val newPos = marsRover.move("b")
    checkPositionAndDirection(marsRover, newPos, Position(0, 4), North())
  }

  it should "move correctly move backward when looking South (y + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(0,0), South())
    val newPos = marsRover.move("b")
    checkPositionAndDirection(marsRover, newPos, Position(0, 1), South())
  }

  it should "move correctly move backward when looking East (x - 1)" in {
    val marsRover = new MarsRover(Grid(), Position(5,0), East())
    val newPos = marsRover.move("b")
    checkPositionAndDirection(marsRover, newPos, Position(4, 0), East())
  }

  it should "move correctly move backward when looking West (x + 1)" in {
    val marsRover = new MarsRover(Grid(), Position(5,0), West())
    val newPos = marsRover.move("b")
    checkPositionAndDirection(marsRover, newPos, Position(6, 0), West())
  }

  it should "Example: The rover is on a 100x100 grid at location (0, 0) and facing NORTH. The rover is given the commands \"ffrff\" and should end up at (2, 2)" in new MarsRoverFixture {
    val newPos = marsRover.move("ffrff")
    newPos.getAsTuple() shouldBe (2,2)
    marsRover.getCurrentDirection() shouldBe East()
  }

  it should "wrap the x position to the number of grid columns if below zero" in {
    val marsRover = new MarsRover(Grid(), Position(99, 0), West())
    val newPos = marsRover.move("b")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), West())
  }

  it should "wrap the x position to zero if greater than the number of grid columns" in {
    val marsRover = new MarsRover(Grid(), Position(0, 0), West())
    val newPos = marsRover.move("f")
    checkPositionAndDirection(marsRover, newPos, Position(99, 0), West())
  }

  it should "wrap the y position to zero if greater than the number of grid rows" in {
    val marsRover = new MarsRover(Grid(), Position(0, 99), North())
    val newPos = marsRover.move("f")
    checkPositionAndDirection(marsRover, newPos, Position(0, 0), North())
  }

  it should "wrap the y position to the number of grid rows if below zero" in {
    val marsRover = new MarsRover(Grid(), Position(0, 0), North())
    val newPos = marsRover.move("b")
    checkPositionAndDirection(marsRover, newPos, Position(0, 99), North())
  }

  private def checkPositionAndDirection(marsRover: MarsRover, movedPos: Position, expectedPos: Position, expectedDir: Direction) = {
    movedPos shouldBe expectedPos
    marsRover.getCurrentDirection() shouldBe expectedDir
    marsRover.getCurrentPosition() shouldBe expectedPos
  }

}