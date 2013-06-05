package ZKRichlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import org.zkoss.zk.ui.*;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Vlayout;
//import org.zkoss.zul.Center;
//import org.zkoss.zul.Hlayout;
//import org.zkoss.zk.ui.event.*;
//import org.zkoss.zul.*;

import AbstractModules.CUIAbstractModuleAnonymousDesktop;
import Services.CAuthenticationService;
import Services.CClassPathLoader;
import Services.CUserCredential;
import Services.IAuthenticationService;
 
public class CRichlet extends GenericRichlet {
	
	static ArrayList<CUIAbstractModuleAnonymousDesktop> RegisteredModulesUIAnonymousDesktop = null;
	
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
			Args.put( "product_version" , "Versión: 1.0.0.0"  );
			
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
    			
    			for ( int intIndexModule = 0; intIndexModule < RegisteredModulesUIAnonymousDesktop.size(); intIndexModule++ ) {

    				CUIAbstractModuleAnonymousDesktop ModuleAnonymousDesktop = RegisteredModulesUIAnonymousDesktop.get( intIndexModule );  
    				
    				ModuleAnonymousDesktop.getUIComponents( Executions.getCurrent(), InnerCenterLayout, null );
    				
    			}
				
				//Executions.getCurrent().createComponents( "~./CRichlet/uiviews/login.zul", InnerCenterLayout, null );
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
	
    @Override
    public void init(RichletConfig config) {

    	super.init(config);
    
    	try {

    		if ( RegisteredModulesUIAnonymousDesktop == null ) {

    			RegisteredModulesUIAnonymousDesktop = new ArrayList<CUIAbstractModuleAnonymousDesktop>();

    			//Component comps = null;

    			CClassPathLoader ClassPathLoader = new CClassPathLoader();

    			String strJarFolder = Executions.getCurrent().getDesktop().getWebApp().getRealPath( "/" ); //servletContext.getRealPath("/"); //getJarFolder();*/

    			ClassPathLoader.LoadClassFiles( strJarFolder + "/WEB-INF/modules/", ".jar", 2 );

    			ServiceLoader<CUIAbstractModuleAnonymousDesktop> sl = ServiceLoader.load( CUIAbstractModuleAnonymousDesktop.class );
    			sl.reload();

    			Iterator<CUIAbstractModuleAnonymousDesktop> it = sl.iterator();

    			while ( it.hasNext() ) {

    				try {

    					CUIAbstractModuleAnonymousDesktop ModuleAnonymousDesktopInstance = it.next();

    					if ( ModuleAnonymousDesktopInstance.InitModule() > 0 ) {
    					
    					   RegisteredModulesUIAnonymousDesktop.add( ModuleAnonymousDesktopInstance );
    					
    					}
    					
    					//comps = ModuleInstance.getUIComponents( Executions.getCurrent(), null, null );

    				}
    				catch ( Exception Ex ) {

    				}

    			}

    		}	

    	}
    	catch ( Exception Ex ) {

    		//System.out.println( Ex.getMessage() );

    	}

    	//initialize resources
    	
    }
    
/* 
    @Override
    public void destroy() {
        super.destroy();
        //destroy resources
    }
*/    
}