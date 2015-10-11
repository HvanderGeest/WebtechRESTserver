package nl.saxion.rest.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import nl.saxion.rest.model.Movie;
import nl.saxion.rest.model.Rating;
import nl.saxion.rest.model.User;

public class MovieTest {

	@Test
	public void testHasRatingFromUser() {
		Movie m = new Movie(420,"half life 2","12-02-2004",90, "Gaben", "best movie evaa");
		User u = new User("Harm", "de", "Docent", "Harm9", "secret");
		m.addRating(new Rating(0.6, u, m.getImdbttNr()));
		
		assertTrue(m.hasRatingFromUser(u));
		assertFalse(m.hasRatingFromUser(new User("pietje", "precies", "", "pietje123", "geheim")));
	}

	@Test
	public void testDeleteRatingFromUser() {
		Movie m = new Movie(420,"half life 2","12-02-2004",90, "Gaben", "best movie evaa");
		User u = new User("Harm", "de", "Docent", "Harm9", "secret");
		m.addRating(new Rating(0.6, u, m.getImdbttNr()));
		
		assertTrue(m.deleteRatingFromUser(u));
		assertFalse(m.deleteRatingFromUser(u));
	}
	
	@Test
	public void testGetAverageRating() {
		Movie m = new Movie(420,"half life 2","12-02-2004",90, "Gaben", "best movie evaa");
		User u = new User("Harm", "de", "Docent", "Harm9", "secret");
		User u2 = new User("Harm", "de", "Docent", "Harm10", "secret");
		m.addRating(new Rating(4, u, m.getImdbttNr()));
		m.addRating(new Rating(5, u2, m.getImdbttNr()));
		assertEquals(4.5, m.getAverageRating(), 0.0);
	}

}
