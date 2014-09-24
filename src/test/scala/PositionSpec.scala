import org.scalatest.{Matchers, FlatSpec}

/**
 * Test class for Position
 *
 * Created by jst on 24.09.14.
 */
class PositionSpec extends FlatSpec with Matchers {
  "Position" should "return the position as tupel (x,y)" in {
    val pos = Position(1, 2)
    pos.getAsTuple() shouldBe (1, 2)
  }
}
