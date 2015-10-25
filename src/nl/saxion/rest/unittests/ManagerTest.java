package nl.saxion.rest.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import nl.saxion.rest.model.Manager;
import nl.saxion.rest.model.Movie;
import nl.saxion.rest.model.Rating;
import nl.saxion.rest.model.Token;
import nl.saxion.rest.model.User;

/**
 * 
 * @author Hugo
 *als er hier errors worden gegenereerd bij het importen
 *hover dan over @Test en klik op add JUnit 4 library to the build path
 */
public class ManagerTest {

	@Test
	public void testUserExists() {
		Manager manager = new Manager();
		User u = new User("pietje", "", "precies", "pietje2", "geheim");
		assertFalse(manager.userExists(u));
		manager.addUser(u);
		assertTrue(manager.userExists(u));
	}

	@Test
	public void testCheckKey() {
		Manager manager = new Manager();
		assertFalse(manager.checkKey("pietje"));
		User u  =new User("pietje", "", "precies", "pietje2", "geheim");
		manager.addUser(u);
		Token token = manager.login("pietje2", "geheim");
		assertTrue(manager.checkKey(token.toString()));
		
	}

	@Test
	public void testGetUserByKey() {
		Manager manager = new Manager();
		User u  = new User("pietje", "", "precies", "pietje2", "geheim");
		manager.addUser(u);
		Token token = manager.login("pietje2", "geheim");
		assertEquals(u, manager.getUserByKey(token.toString()));
	}

	@Test
	public void testGetMovie() {
		Manager manager = new Manager();
		Movie m = new Movie(333, "intouchable", "2008", 120, "fransman","awesome movie");
		manager.addMovie(m);
		assertEquals(manager.getMovie(m.getImdbttNr()),m);
	}


	@Test
	public void testQueryMovie() {
		Manager manager = new Manager();
		Movie m = new Movie(333, "intouchable", "11-01-2008", 120, "fransman","awesome movie");
		manager.addMovie(m);
		assertTrue(manager.queryMovie("intouchable", null, null).contains(m));
		assertTrue(manager.queryMovie(null, "11-01-2008", null).contains(m));
		assertTrue(manager.queryMovie(null, null, "fransman").contains(m));
		assertFalse(manager.queryMovie(null, null, "fransman2").contains(m));
		
	}

	@Test
	public void testQueryUser() {
		Manager manager = new Manager();
		User u  = new User("pietje", "", "precies", "pietje2", "geheim");
		manager.addUser(u);
		assertTrue(manager.queryUser("pietje2", null, null).contains(u));
		assertTrue(manager.queryUser(null, "pietje", null).contains(u));
		assertTrue(manager.queryUser(null, null, "precies").contains(u));
		assertFalse(manager.queryUser(null, "henk", null).contains(u));
	}

	@Test
	public void testGetMyRatingForMovie() {
		Manager manager = new Manager();
		User u  = new User("pietje", "", "precies", "pietje2", "geheim");
		Movie m = new Movie(333, "intouchable", "2008", 120, "fransman","awesome movie");
		manager.addMovie(m);
		manager.addUser(u);
		Token t = manager.login("pietje2", "geheim");
		Rating r = new Rating(0.5, u, m.getImdbttNr());
		m.addRating(r);
		manager.addUser(u);
		assertEquals(r, manager.getMyRatingForMovie(t.toString(), m.getImdbttNr()));
		
	}

	@Test
	public void testGetMyRatings() {
		Manager manager = new Manager();
		User u  = new User("pietje", "", "precies", "pietje2", "geheim");
		Movie m = new Movie(333, "intouchable", "2008", 120, "fransman","awesome movie");
		manager.addMovie(m);
		manager.addUser(u);
		Token t = manager.login("pietje2", "geheim");
		Rating r = new Rating(0.5, u, m.getImdbttNr());
		m.addRating(r);
		manager.addUser(u);
		assertTrue(manager.getMyRatings(t.toString()).contains(r));
		
		
	}

	@Test
	public void testGetMoviesWithRating() {
		Manager manager = new Manager();
		Movie movie = new Movie(333, "intouchable2", "2008", 120, "fransman","awesome movie");
		Movie movie2 = new Movie(333, "intouchable", "2008", 120, "fransman","awesome movie");
		movie.addRating(new Rating(0.5, new User("pietje", "", "precies", "pietje2", "geheim") , movie.getImdbttNr()));
		manager.addMovie(movie2);
		manager.addMovie(movie);
		assertTrue(manager.getMoviesWithRating().contains(movie));
		assertFalse(manager.getMoviesWithRating().contains(movie2));
	}

}
