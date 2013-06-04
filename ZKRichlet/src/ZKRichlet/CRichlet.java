package ZKRichlet;

import java.util.HashMap;

import org.zkoss.zk.ui.*;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Vlayout;
//import org.zkoss.zul.Center;
//import org.zkoss.zul.Hlayout;
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
			
			HashMap<String,String> Args = new HashMap<String,String>(); 
			
			Args.put( "background_image" , "~./CRichlet/images/product_splash_screen_01.png" );
			Args.put( "product_logo" , "~./CRichlet/images/product_logo.png" );
			Args.put( "product_name" , "Aurora" );
			Args.put( "product_edition" , "Enterprise Resource Planning" );
			Args.put( "product_target" , "Business" );
			Args.put( "product_slogan" , "\"A serius ERP\"" );
			Args.put( "product_version" , "Versión: 1.0.0.0 Chufluca"  );
			
			Components = Executions.getCurrent().createComponents( "~./CRichlet/uiviews/splash_screen.zul", Args );
			
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
    			
				Vlayout InnerCenterLayout = (Vlayout) Components[ 0 ].getFellow( "InnerCenterLayout" );
    			
    			Executions.getCurrent().createComponents( "~./CRichlet/uiviews/login.zul", InnerCenterLayout, null );
    			//Executions.getCurrent().createComponents( "~./CRichlet/uiviews/Window1.zul", InnerCenterLayout, null );
    			
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