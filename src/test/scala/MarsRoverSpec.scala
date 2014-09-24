import collection.mutable.Stack
import org.scalatest._

/**
 * Test class for MarsRover
 *
 * Created by jst on 24.09.14.
 */
class MarsRoverSpec extends FlatSpec with Matchers {

  "A MarsRover" should "be created" in {
    val marsRover = new MarsRover()
    marsRover should not be Nil
  }

}