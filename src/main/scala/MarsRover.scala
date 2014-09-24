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
class MarsRover(val grid: Grid, val startPosition: Position = Position(0,0), val direction: Direction = North()) {

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
    command.foreach(_ match {
      case 'f' => currentPosition = Position(currentPosition.x, currentPosition.y + 1)
      case 'b' => currentPosition = Position(currentPosition.x, currentPosition.y - 1)
      case 'l' => currentDirection = West()
      case _ => currentPosition
    })

    currentPosition
  }
}
