package Services;

import java.io.Serializable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class CAuthenticationService implements IAuthenticationService, Serializable {
	

	private static final long serialVersionUID = 4552054336512568195L;


	public CUserCredential getUserCredential(){
	
		Session CurrentSession = Sessions.getCurrent();
		
		CUserCredential UserCredential = (CUserCredential) CurrentSession.getAttribute( "userCredential" );

		if( UserCredential == null ){
			
			UserCredential = new CUserCredential(); //new a anonymous user and set to session
			
			CurrentSession.setAttribute( "userCredential", UserCredential );
			
		}
		
		return UserCredential;
		
	}
	

	public boolean login( String strUserName, String strPassword ) {

		if ( strUserName.equals( "sirlordt" ) && strPassword.equals( "123456" ) ) {
			
			Session CurrentSession = Sessions.getCurrent();
			
			CUserCredential UserCredential =  new CUserCredential( "sirlordt", "El papa de los helados" );
			
			CurrentSession.setAttribute( "userCredential", UserCredential );
				
			return true;
			
		}
		
		
		return false;
		
	}


	public void logout() {

		Session currenSession = Sessions.getCurrent();
		
		currenSession.removeAttribute( "userCredential" );
		
	}
	
}
