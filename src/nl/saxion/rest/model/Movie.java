package nl.saxion.rest.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement
public class Movie {
	public static int MyinternFollowNr = 1;
	public static int internFollowNr;
	private int imdbttNr;
	private String title;
	private String date;
	private int duration;
	private String director;
	private String description;
	
	public Movie(int imdbttNr, String title, String date, int duration, String director, String description){
		internFollowNr = MyinternFollowNr;
		MyinternFollowNr++;
		this.imdbttNr = imdbttNr;
		this.title = title;
		this.date = date;
		this.duration = duration;
		this.director = director;
		this.description = description;
	}
	
	public Movie(){
		
	}
	@JsonIgnore
	@XmlTransient
	public int getInternFollowNr() {
		return internFollowNr;
	}
	

	public int getImdbttNr() {
		return imdbttNr;
	}

	public void setImdbttNr(int imdbttNr) {
		this.imdbttNr = imdbttNr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	

	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
