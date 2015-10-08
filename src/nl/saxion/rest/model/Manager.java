package nl.saxion.rest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
	private String s;
	private List<User> users = new ArrayList<User>();
	private Map<Token,User> keyMap = new HashMap<Token,User>();
	private ArrayList<Movie> movies = new ArrayList<>();
	
	public Manager() {
		this.s = "test";
		users.add(new User("Harm", "de", "Docent", "Harm9", "secret"));
		movies.add(new Movie(420,"half life 2","12-02-2004",90, "Gaben", "best movie evaa"));
		movies.add(new Movie(430,"half life 3","?",90, "Gaben Noell", "gamen"));
	}
	
	public String getTest() {
		return this.s;
	}
	
	public void addUser(User u) {
		users.add(u);
	}
	
	public void getUser() {
		
	}
	
	public boolean checkKey(String key) {
		if(keyMap.containsKey(key)) {
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
		if (checkKey(key)) {
			return keyMap.get("key");
		} else {
			return null;
		}
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
}
