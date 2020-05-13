package m.pgmacdesign.dagger2examples.models;

import com.google.gson.annotations.SerializedName;

public class User {
	
	@SerializedName("id")
	private int id;
	@SerializedName("username")
	private String username;
	@SerializedName("email")
	private String email;
	@SerializedName("website")
	private String website;
	
	public int getId() {
		return id;
	}
	
	public User(){
	
	}
	
	public User(int id, String username, String email, String website) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.website = website;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
}