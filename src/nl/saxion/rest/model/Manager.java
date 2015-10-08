package nl.saxion.rest.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
	private String s;
	private List<User> users = new ArrayList<User>();
	private Map<Token,User> keyMap = new HashMap<Token,User>();
	
	public Manager() {
		this.s = "test";
		users.add(new User("Harm", "de", "Docent", "Harm9", "secret"));
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
}
