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
		Movie m = new Movie(420,"half life 2","12-02-2004",90, "Gaben", "best movie evaa");
		movies.add(m);
		
		movies.add(new Movie(430,"half life 3","12-02-2004",90, "Gaben Noell", "gamen"));
		
		//user + token for testing
		User test = new User("test", "test", "test", "test", "test");
		users.add(test);
		m.addRating(new Rating(0.9, test, m.getImdbttNr()));
		keyMap.put(new Token("test_token"), test);
	}
	
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
	
	public void addUser(User u) {
		users.add(u);
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
	
	
	
	public List<Movie> queryMovie(String title, String date, String director){
		List<Movie> result = new ArrayList<>();
		if(title != null){
			for(Movie movie : movies){
				if(movie.getTitle().equalsIgnoreCase(title)){
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
	
	public Rating getMyRatingForMovie(String usertoken, int imdbttNr){
		Movie m = getMovie(imdbttNr);
		if(m == null){
			return null;
		}
		User u = keyMap.get(getToken(usertoken));
		System.out.println(u.getFirstname());
		for(Rating r : m.getRatings()){
			System.out.println(r.getUser().getFirstname() + r.getRating());
			if(r.isFromUser(u)){
				return r;
			}
		}
		System.out.println("not found");
		return new Rating();
		
	}
	
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
	
	public List<Movie> getMoviesWithRating(){
		List<Movie> result = new ArrayList<>();
		for(Movie m : movies){
			if(m.getRatings().isEmpty()){
				break;
			}
			result.add(m);
		}
		return result;
	}
	
}
