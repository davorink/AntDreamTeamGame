import static org.junit.Assert.*;
import org.junit.Test;
/**
 * jUnit tester for Pos
 * @author D Kopic
 *
 */
public class PosTest {
	@Test
	public void testPos() throws PosException {
		Pos p = new Pos(0,0);
		assertNotNull(p);
	}

	@Test(expected=Exception.class)
	public void testPos2() throws PosException  {
		Pos p = new Pos(-1,0);
	}
	
	@Test(expected=Exception.class)
	public void testPos3() throws PosException  {
		Pos p = new Pos(0,-1);
	}
	
	@Test
	public void testGetX() throws PosException  {
		Pos p = new Pos(0,0);
		assertEquals(0, p.getX());
	}

	@Test
	public void testGetY() throws PosException  {
		Pos p = new Pos(0,0);
		assertEquals(0, p.getY());
	}
	
	@Test
	public void testSetX() throws PosException  {
		Pos p = new Pos(0,0);
		p.setX(5);
		assertEquals(5, p.getX());
	}

	@Test(expected=Exception.class)
	public void testSetX2() throws PosException  {
		Pos p = new Pos(0,0);
		p.setX(-1);
	}

	@Test
	public void testSetY() throws PosException  {
		Pos p = new Pos(0,0);
		p.setY(5);
		assertEquals(5, p.getY());
	}

	@Test(expected=Exception.class)
	public void testSetY2() throws PosException  {
		Pos p = new Pos(0,0);
		p.setY(-1);
	}
}
