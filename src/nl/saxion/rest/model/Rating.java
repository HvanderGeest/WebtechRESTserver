package nl.saxion.rest.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class Rating {
	private double rating;
	private User user;
	private int imdbttNr;
	
	/**
	 * Construct a Rating object
	 * @param rating the rating value, has to be between 0.5 and 5
	 * @param user the user who made the rating
	 * @param imdbttNr the number of the movie the rating is for
	 */
	public Rating(double rating, User user, int imdbttNr){
		this.rating = rating;
		this.user = user;
		this.imdbttNr = imdbttNr;
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
	
	public void removeRating(){
		this.rating = 0;
	}
	
	public boolean isFromUser(User u){
		return u.equals(user);
	}


	public int getImdbttNr() {
		return imdbttNr;
	}


	public void setImdbttNr(int imdbttNr) {
		this.imdbttNr = imdbttNr;
	}

	
}
