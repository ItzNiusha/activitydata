package model;

public class UserModel {

	private final String userName = "niusha";
	private final String password = "niusha";

	public boolean authenticate(String username, String password) {
		return this.userName.equals(username) && this.password.equals(password);
	}

}
