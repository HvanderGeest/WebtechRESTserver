package nl.saxion.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class Rating {
	private double rating;
	private User user;
	
	public Rating(double rating, User user){
		this.setRating(rating);
		this.setUser(user);
	}
	
	
	/**
	 * an empty constructor to ensure conversion to xml/json works
	 */
	public Rating(){
		
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}

	@XmlTransient
	@JsonIgnore
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

}
