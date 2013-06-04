package ZKRichlet;

import org.zkoss.zk.ui.*;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
//import org.zkoss.zk.ui.event.*;
//import org.zkoss.zul.*;

import Services.CAuthenticationService;
import Services.CUserCredential;
import Services.IAuthenticationService;
 
public class CRichlet extends GenericRichlet {
	
	IAuthenticationService authService = null;
	
	{
		
		authService = new CAuthenticationService();
		
	}

	public void service( Page page ) {
    
    	CUserCredential UserCredential = authService.getUserCredential();

    	Component[] Components = null;
		
		Session session = Sessions.getCurrent();
    	
		if ( session.getAttribute( "app_initiated" ) == null ) { //Splash screen
			
			Components = Executions.getCurrent().createComponents( "~./CRichlet/uiviews/splash_screen.zul", null );
			
		}
		else if ( UserCredential == null || UserCredential.isAnonymous() ) { //Anonymous desktop

    		try {

    			Components = Executions.getCurrent().createComponents( "~./CRichlet/uiviews/anonymous_desktop.zul", null );

    			Borderlayout DesktopLayout = (Borderlayout) Components[0].getFellow( "DesktopLayout" );
    			
				if ( DesktopLayout != null ) {
					
					if ( Integer.parseInt( (String) session.getAttribute( "browserwidth" ) )  > 1024 )
					   DesktopLayout.setWidth( (String) session.getAttribute( "browserwidth" ) );

					if ( Integer.parseInt( (String) session.getAttribute( "browserheight" ) )  > 768 )
						DesktopLayout.setHeight( (String) session.getAttribute( "browserheight" ) );
				
				}
    			
    			Center CenterZone = DesktopLayout.getCenter();
    			
    			Executions.getCurrent().createComponents( "~./CRichlet/uiviews/login.zul",  CenterZone, null );
    			
    		}
    		catch ( Exception Ex ) {
    			
    			System.out.println( Ex );
    			
    		}
            
		}
		else { //Logged desktop
			
    		try {

    			Components = Executions.getCurrent().createComponents( "~./CRichlet/uiviews/desktop.zul", null );

    		}
    		catch ( Exception Ex ) {
    			
    			
    		}
			
		}
    	
		for ( int intIndexComponent = 0; intIndexComponent < Components.length; intIndexComponent++ ) {
    		
			Components[ intIndexComponent ].setPage( page );
			
		}

		
		//page.setTitle("Richlet Test");
 
        /*final Window w = new Window("Richlet Test", "normal", false);
        new Label("Hello World!").setParent(w);
        final Label l = new Label();
        l.setParent(w);
 
        final Button b = new Button("Change");
        b.addEventListener( Events.ON_CLICK, new EventListener<Event>() {
        
        	int count;
        	
            public void onEvent(Event evt) {
                
            	l.setValue("" + count );
            	
            }
            
        });
        
        b.setParent(w);
 
        w.setPage(page);*/
        
    }
 /*
    @Override
    public void init(RichletConfig config) {
        super.init(config);
        //initialize resources
    }
 
    @Override
    public void destroy() {
        super.destroy();
        //destroy resources
    }
*/    
}