import static org.junit.Assert.*;
import org.junit.Test;
/**
 * jUNit tester for TeamColor
 * @author D Kopic
 *
 */
public class TeamColorTest {

	@Test
	public void testOtherColor() {
		assertEquals(TeamColor.BLACK, TeamColor.otherColor(TeamColor.RED));
		assertEquals(TeamColor.RED, TeamColor.otherColor(TeamColor.BLACK));
	}

}
