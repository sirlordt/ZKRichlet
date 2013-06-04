package Services;

public interface IAuthenticationService {

	/**login with account and password**/
	public boolean login(String account, String password);
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential**/
	public CUserCredential getUserCredential();
	
	
}
