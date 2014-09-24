/**
 * Grid class
 * (0,0) is the left lower corner
 *
 * Created by jst on 24.09.14.
 */
case class Grid(columns: Integer = 100, rows: Integer = 100) {

  require(columns > 0)
  require(rows > 0)

  def getAsTuple(): (Integer, Integer) = (columns, rows)
}
