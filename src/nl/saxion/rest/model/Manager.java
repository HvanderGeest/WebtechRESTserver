package nl.saxion.rest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
	private String s;
	private List<User> users = new ArrayList<User>();
	private Map<Token,User> keyMap = new HashMap<Token,User>();
	private List<Movie> movies = new ArrayList<>();
	/**
	 * manger object with some test values
	 **/
	public Manager() {
		this.s = "test";
		users.add(new User("Harm", "de", "Docent", "Harm9", "secret"));
		Movie m = new Movie(420,"The Martian","12-02-2004",90, "Gaben", "best movie evaa");
		movies.add(m);
		
		movies.add(new Movie(430,"half life 3","12-02-2004",90, "Gaben Noell", "gamen"));
		Movie m2 = new Movie(440,"Gravity","12-02-2013",120,"Joerge clooney","space brah");
		movies.add(m2);
		//user + token for testing
		User test = new User("Pieter", "de", "Knouwe", "test", "test");
		users.add(test);
		m.addRating(new Rating(1.0, test, m.getImdbttNr()));
		m2.addRating(new Rating(3.5,test,m2.getImdbttNr() ));
		Movie m3 = new Movie(450, "Inside Out", "2015", 124, "whatever", "sick movie");
		Movie m4 = new Movie(460,"Frozen","2013",90,"princes","fun movie");
		Movie m5 = new Movie(470,"Up", "2013",90,"old man", "baloons");
		m3.addRating(new Rating(4.5, test, m3.getImdbttNr()));
		m4.addRating(new Rating(3.5,test, m4.getImdbttNr()));
		m5.addRating(new Rating(5.0, test, m5.getImdbttNr()));
		movies.add(m3);
		movies.add(m4);
		movies.add(m5);
		
		keyMap.put(new Token("test_token"), test);
	}
	/**
	 * checks wheter a users nickname exists in the system
	 * @param u the user that should be checked
	 * @return true if the user exists, false otherwise
	 */
	public boolean userExists(User u){
		for(User user : users){
			if(user.getNickname().equals(u.getNickname())){
				return true;
			}
		}
		return false;
	}
	
	public String getTest() {
		return this.s;
	}
	/**
	 * add a user
	 * @param u user you want to add
	 */
	public void addUser(User u) {
		users.add(u);
	}
	
	/**
	 * gets the token for a key string
	 * @param key the key in string format
	 * @return Token for the key string
	 */
	private Token getToken(String key){
		for( Token t : keyMap.keySet()){
			if(t.toString().equals(key)){
				return t;
			}
		}
		return null;
		
	}
	/**
	 * checks if a key exists in the system
	 * @param key key that should be checked
	 * @return true if the key exists, false otherwise
	 */
	public boolean checkKey(String key) {
		if(keyMap.containsKey(getToken(key))) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<User> getUserList() {
		return this.users;
	}
	
	public List<Movie> getMovieList(){
		return this.movies;
	}
	/**
	 * get a user for a certain key
	 * @param key the key associated with the user
	 * @return
	 */
	public User getUserByKey(String key) {
		return keyMap.get(getToken(key));
		
	}
	/**
	 * gets a token for a user
	 * @param user username of the user
	 * @param password password of the user
	 * @return Token for the user
	 */
	public Token login(String user, String password) {
		//checks wheter a token is already assigned to the user
		Token t = getTokenForUser(user, password);
		if(t != null) return t;
		for(User u : users) {

			if (u.getNickname().equals(user) && u.getPassword().equals(password)) {
				//creates a new token
				Token temp = new Token();
				keyMap.put(temp, u);
				return temp;
			}
		}
		//not found
		return null;
	}
	/**
	 * checks if the user has already been given a token
	 * @param userName nickname of the user
	 * @param password password of the user
	 * @return token of the user, returns null if there isn't a token asssigned to the user.
	 */
	private Token getTokenForUser(String userName, String password){
		for(Token t : keyMap.keySet()){
			User u = keyMap.get(t);
			if (u.getNickname().equals(userName) && u.getPassword().equals(password)) {
				return t;
			}
		}
		return null;
	}
	/**
	 * gets a movie for a certain imdbttNr
	 * @param imdbttNr imdbttNr of the movie
	 * @return the found movie, returns null if the movie isn't found
	 */
	public Movie getMovie(int imdbttNr){
		for(Movie m : movies){
			if(m.getImdbttNr() == imdbttNr){
				return m;
			}
		}
		//not found
		return null;
	}
	/**
	 * adds a movie to the system
	 * @param movie movie that should be added
	 */
	public void addMovie(Movie movie){
		movies.add(movie);
		System.out.println("movie added");
	}
	
	
	/**
	 * get a list of movies for the given parameters
	 * @param title title of the movie
	 * @param date date of the movie
	 * @param director director of the movie
	 * @return List of the found movies
	 */
	public List<Movie> queryMovie(String title, String date, String director){
		List<Movie> result = new ArrayList<>();
		if(title != null){
			for(Movie movie : movies){
				if(movie.getTitle().toLowerCase().contains(title.toLowerCase())){
					if(date != null && !movie.getDate().equals(date)){
						break;
					}
					if(director != null && !movie.getDirector().equalsIgnoreCase(director)){
						break;
					}
					result.add(movie);
					
				}
			}
		} else if(date != null){
			//title is null
			for(Movie m : movies){
				if(m.getDate().equalsIgnoreCase(date)){
					if(director != null && !m.getDirector().equalsIgnoreCase(director)){
						break;
					}
					result.add(m);
				}
			}
		} else {
			for(Movie m : movies){
				//title and date are null
				if(m.getDirector().equalsIgnoreCase(director)){
					result.add(m);
				}
			}
		}
		return result;
	}
	/**
	 * get a list of users for the given parameters
	 * @param nickname nickname of the user
	 * @param firstname firstname of the user
	 * @param lastname lastname of the user
	 * @return List with the found users
	 */
	public List<User> queryUser(String nickname, String firstname, String lastname){
		List<User> result = new ArrayList<>();
		if(nickname != null){
			for(User u : users){
				if(u.getNickname().equals(nickname)){
					result.add(u);
				}
			}
			
		} else if(firstname != null){
			for(User u : users){
				if(u.getFirstname().equalsIgnoreCase(firstname)){
					if(lastname != null && !u.getLastname().equalsIgnoreCase(lastname)){
						break;
					}
					result.add(u);
				}
			}
		} else {
			for(User u : users){
				if(u.getLastname().equalsIgnoreCase(lastname)){
					result.add(u);
				}
			}
		}
		return result;
	}
	/**
	 * get the rating for a movie of the logged in user
	 * @param usertoken usertoken of the user
	 * @param imdbttNr imdbttNr of the movie
	 * @return Rating for the movie
	 */
	public Rating getMyRatingForMovie(String usertoken, int imdbttNr){
		Movie m = getMovie(imdbttNr);
		if(m == null){
			return null;
		}
		User u = keyMap.get(getToken(usertoken));
		System.out.println(u.getFirstname());
		for(Rating r : m.getRatings()){
			if(r.isFromUser(u)){
				return r;
			}
		}
		System.out.println("not found");
		return new Rating();
		
	}
	/**
	 * list of all the ratings of the user
	 * @param token the token of the user
	 * @return list of all the ratings
	 */
	public List<Rating> getMyRatings(String token){
		List<Rating> result = new ArrayList<>();
		for(Movie m : movies){
			for(Rating r : m.getRatings()){
				if(r.getUser().equals(getUserByKey(token))){
					result.add(r);
				}
			}
		}
		return result;
	}
	/**
	 * get a list of all movies that have a rating
	 * @return list of movies with a rating
	 */
	public List<Movie> getMoviesWithRating(){
		List<Movie> result = new ArrayList<>();
		for(Movie m : movies){
			if(!m.getRatings().isEmpty()){
				result.add(m);
			}
			
		}
		return result;
	}
	
}
