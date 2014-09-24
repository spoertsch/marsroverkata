/**
 * Created by jst on 24.09.14.
 */
class MarsRover(val gridColumns: Integer = 100, val gridRows: Integer = 100) {

  require(gridColumns >= 0)
  require(gridRows >= 0)

  def getGrid() : (Integer, Integer) = (gridColumns, gridRows)

}
