import org.scalatest.{Matchers, FlatSpec}

/**
 * Test class for Grid
 *
 * Created by jst on 24.09.14.
 */
class GridSpec extends FlatSpec with Matchers {

  "A Grid" should "return the grid as tuple (x,y)" in {
    val grid = Grid(1,2)
    grid.getAsTuple() shouldBe (1,2)
  }

  it should "have a default value of 100 for columns and rows" in {
    val grid = Grid()
    grid.getAsTuple() shouldBe (100, 100)
  }

  it should "not allow a negative grid column count" in {
    a [IllegalArgumentException] should be thrownBy {
      Grid(-1, 100)
    }
  }

  it should "not allow a negative grid row count" in {
    a [IllegalArgumentException] should be thrownBy {
      Grid(100, -1)
    }
  }

  it should "not allow zero column count" in {
    a [IllegalArgumentException] should be thrownBy {
      Grid(0, 100)
    }
  }

  it should "not allow zero row count" in {
    a [IllegalArgumentException] should be thrownBy {
      Grid(100, 0)
    }
  }
}
