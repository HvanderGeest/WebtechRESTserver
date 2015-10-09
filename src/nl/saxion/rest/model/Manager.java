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
	
	public Manager() {
		this.s = "test";
		users.add(new User("Harm", "de", "Docent", "Harm9", "secret"));
		movies.add(new Movie(420,"half life 2","12-02-2004",90, "Gaben", "best movie evaa"));
		movies.add(new Movie(430,"half life 3","?",90, "Gaben Noell", "gamen"));
		
		//user + token for testing
		users.add(new User("test", "test", "test", "test", "test"));
		keyMap.put(new Token("test_token"), new User("test", "test", "test", "test", "test"));
	}
	
	public String getTest() {
		return this.s;
	}
	
	public void addUser(User u) {
		users.add(u);
	}
	
	public void getUser() {
		
	}
	
	private Token getToken(String key){
		for( Token t : keyMap.keySet()){
			if(t.toString().equals(key)){
				return t;
			}
		}
		return null;
		
	}
	
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
	
	public User getUserByKey(String key) {
		return keyMap.get(getToken(key));
		
	}
	
	public Token login(String user, String password) {
		for(User u : users) {

			if (u.getNickname().equals(user) && u.getPassword().equals(password)) {
				Token temp = new Token();
				keyMap.put(temp, u);
				return temp;
			}
		}
		
		return null;
	}
	
	public Movie getMovie(int imdbttNr){
		for(Movie m : movies){
			if(m.getImdbttNr() == imdbttNr){
				return m;
			}
		}
		//not found
		return null;
	}
	
	public void addMovie(Movie movie){
		movies.add(movie);
		System.out.println("movie added");
	}
	
	public double getAvarageRating(Movie movie){
		double total = 0;
		for(Rating rating : movie.getRatings()){
			total += rating.getRating();
		}
		
		return total / movie.getRatings().size();
	}
	
}
