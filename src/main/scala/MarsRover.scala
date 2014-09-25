/**
 *
 * http://craftsmanship.sv.cmu.edu/katas/mars-rover-kata
 *
 * - Develop an api that moves a rover around on a grid.
 * - You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing.
 * - The rover receives a character array of commands.
 * - Implement commands that move the rover forward/backward (f,b).
 * - Implement commands that turn the rover left/right (l,r).
 * - Implement wrapping from one edge of the grid to another. (planets are spheres after all)
 * - Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle, the rover moves up to the last possible point and reports the obstacle.
 * - Example: The rover is on a 100x100 grid at location (0, 0) and facing NORTH. The rover is given the commands "ffrff" and should end up at (2, 2)
 * - Tips: use multiple classes and TDD
 *
 * Created by jst on 24.09.14.
 */
class MarsRover(val grid: Grid, val startPosition: Position = Position(0, 0), val direction: Direction = North()) {

  require(startPosition.x >= 0)
  require(startPosition.y >= 0)

  require(startPosition.x < grid.columns)
  require(startPosition.y < grid.rows)

  private var currentPosition = startPosition
  private var currentDirection = direction

  def getCurrentPosition(): Position = currentPosition

  def getCurrentDirection(): Direction = currentDirection

  def move(command: String): Position = move(command.toCharArray)

  def move(command: Array[Char]): Position = {

    def wrapPosition(x: Integer, y: Integer): Position = {
      var tmpX = x
      if (x >= grid.columns) tmpX = 0
      if (x < 0) tmpX = grid.columns - 1

      var tmpY = y
      if (y >= grid.rows) tmpY = 0
      if (y < 0) tmpY = grid.rows - 1

      Position(tmpX, tmpY)
    }

    command.foreach(_ match {
      case 'f' => currentDirection match {
        case North() => currentPosition = wrapPosition(currentPosition.x, currentPosition.y + 1)
        case South() => currentPosition = wrapPosition(currentPosition.x, currentPosition.y - 1)
        case West() => currentPosition = wrapPosition(currentPosition.x - 1, currentPosition.y)
        case East() => currentPosition = wrapPosition(currentPosition.x + 1, currentPosition.y)
      }
      case 'b' => currentDirection match {
        case North() => currentPosition = wrapPosition(currentPosition.x, currentPosition.y - 1)
        case South() => currentPosition = wrapPosition(currentPosition.x, currentPosition.y + 1)
        case West() => currentPosition = wrapPosition(currentPosition.x + 1, currentPosition.y)
        case East() => currentPosition = wrapPosition(currentPosition.x - 1, currentPosition.y)
      }
      case 'l' => currentDirection match {
        case North() => currentDirection = West()
        case West() => currentDirection = South()
        case South() => currentDirection = East()
        case East() => currentDirection = North()
        case _ =>
      }
      case 'r' => currentDirection match {
        case North() => currentDirection = East()
        case East() => currentDirection = South()
        case South() => currentDirection = West()
        case West() => currentDirection = North()
        case _ =>
      }
      case _ => currentPosition
    })

    currentPosition
  }


}
