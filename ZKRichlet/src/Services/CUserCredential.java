package Services;

import java.io.Serializable;
import java.util.HashSet;

public class CUserCredential implements Serializable {

	private static final long serialVersionUID = 6617934674261888027L;
	
	protected String strUserName;
	protected String strDescription;
	protected int intIdSystemGroup;
	protected int intIdSystemOperator;
	protected boolean bIsSystemAdmin;
    protected int intSecurityLevel;
	
	protected HashSet<String> CredentialRoles = new HashSet<String>();

	public CUserCredential( String strAccount, String strDescription ) {
		
		this.strUserName = strAccount;
		this.strDescription = strDescription;
		this.intIdSystemGroup = -1;
		this.intIdSystemOperator = -1;
		this.bIsSystemAdmin = false;
		this.intSecurityLevel = -1;
		
	}

	public CUserCredential() {
	
		this.strUserName = "anonymous";
		this.strDescription = "Anonymous";
		this.intIdSystemGroup = -1;
		this.intIdSystemOperator = -1;
		this.bIsSystemAdmin = false;
		this.intSecurityLevel = -1;
		
		CredentialRoles.add( "anonymous" );
		
	}

	public boolean isAnonymous() {
		
		return hasRole( "anonymous" ) || strUserName.equals( "anonymous" );
		
	}

	public String getUserName() {
		
		return strUserName;
		
	}

	public void setUserName( String strUserName ) {
		
		this.strUserName = strUserName;
		
	}

	public String getDescription() {
		
		return strDescription;
		
	}

	public void setDescription( String strDescription ) {
		
		this.strDescription = strDescription;
		
	}

	public int getIdSystemGroup() {
		
		return intIdSystemGroup;
		
	}
	
	public void setIdSystemGroup( int intIdSystemGroup ) {
		
		this.intIdSystemGroup = intIdSystemGroup;
		
	}

	public int getIdSystemOperator() {
		
		return intIdSystemGroup;
		
	}
	
	public void setIdSystemOperato( int intIdSystemOperator ) {
		
		this.intIdSystemOperator = intIdSystemOperator;
		
	}

	public boolean getIsSystemAdmin() {
		
		return bIsSystemAdmin;
		
	}
	
	public void setIsSystemAdmin( boolean bIsSystemAdmin ) {
		
		this.bIsSystemAdmin = bIsSystemAdmin;
		
	}
	
	public int getSecurityLevel() {
		
		return intSecurityLevel;
		
	}

	public void setSecurityLevel( int intSecurityLevel ) {
		
		this.intSecurityLevel = intSecurityLevel;
		
	} 
	
	public boolean hasRole( String strRole ){
		
		return CredentialRoles.contains( strRole );
		
	}
	
	public void addRole( String strRole ){
		
		CredentialRoles.add( strRole );
		
	}

	public void removeRole( String strRole ){
		
		CredentialRoles.remove( strRole );
		
	}
	
}
