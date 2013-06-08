package ZKRichlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.impl.PollingServerPush;
import org.zkoss.zk.ui.sys.DesktopCtrl;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Vlayout;

import AbstractModules.CUIAbstractModuleAnonymousDesktop;
import AbstractModules.CUIAbstractModule.DesktopPosition;
import AbstractModules.CUIAbstractModuleDesktop;

import Services.CAuthenticationService;
import Services.CClassPathLoader;
import Services.CUserCredential;
import Services.IAuthenticationService;
 
public class CRichlet extends GenericRichlet {
	
	static ArrayList<CUIAbstractModuleAnonymousDesktop> RegisteredModulesUIAnonymousDesktop = null;
	static ArrayList<CUIAbstractModuleDesktop> RegisteredModulesUIDesktop = null;
	
	IAuthenticationService authService = null;
	
	{
		
		authService = new CAuthenticationService();
		
	}

	public void service( Page page ) {
    
    	CUserCredential UserCredential = authService.getUserCredential();

    	ArrayList<Component> CreatedComponents = new ArrayList<Component>();
		
		Session session = Sessions.getCurrent();
		
		if ( session.getAttribute( "app_initiated" ) == null ) { //Splash screen
			
			HashMap<String,Object> Args = new HashMap<String,Object>(); 
			
			Args.put( "background_image" , "~./CRichlet/images/product_splash_screen_01.png" );
			Args.put( "product_logo" , "~./CRichlet/images/product_logo.png" );
			Args.put( "product_name" , "Aurora" );
			Args.put( "product_edition" , "Enterprise Resource Planning" );
			Args.put( "product_target" , "Business" );
			Args.put( "product_slogan" , "\"A serius ERP\"" );
			Args.put( "product_version" , "Versión: 1.0.0.0"  );
			Args.put( "MainModule", this );
			//Args.put( "RegisteredModulesUIAnonymousDesktopCount", RegisteredModulesUIAnonymousDesktop.size() );
			
			CreatedComponents.add( Executions.getCurrent().createComponents( "~./CRichlet/uiviews/splash_screen.zul", null, Args ) );
			
		}
		else if ( UserCredential == null || UserCredential.isAnonymous() ) { //Anonymous desktop

    		try {

    			Component AnonymousDesktop = Executions.getCurrent().createComponents( "~./CRichlet/uiviews/anonymous_desktop.zul", null, null );

    			Borderlayout DesktopLayout = (Borderlayout) AnonymousDesktop.getFellow( "DesktopLayout" );
    			
				if ( DesktopLayout != null ) {
					
					if ( Integer.parseInt( (String) session.getAttribute( "browserwidth" ) )  > 1024 )
					    DesktopLayout.setWidth( (String) session.getAttribute( "browserwidth" ) );

					if ( Integer.parseInt( (String) session.getAttribute( "browserheight" ) )  > 768 )
						DesktopLayout.setHeight( (String) session.getAttribute( "browserheight" ) );
				
				}
    			
				Hlayout TopHLayout = (Hlayout) AnonymousDesktop.getFellowIfAny( "TopHLayout" );
				
				Vlayout LeftVLayout = (Vlayout) AnonymousDesktop.getFellowIfAny( "LeftVLayout" );

				Hlayout BottomHLayout = (Hlayout) AnonymousDesktop.getFellowIfAny( "BottomHLayout" );

				Vlayout RightVLayout = (Vlayout) AnonymousDesktop.getFellowIfAny( "RightVLayout" );

				Vlayout InnerCenterLayout = (Vlayout) AnonymousDesktop.getFellowIfAny( "InnerCenterLayout" );
    			
    			for ( int intIndexModule = 0; intIndexModule < RegisteredModulesUIAnonymousDesktop.size(); intIndexModule++ ) {

    				try {
    				
    					CUIAbstractModuleAnonymousDesktop UIModuleAnonymousDesktopInstance = RegisteredModulesUIAnonymousDesktop.get( intIndexModule );  

    					if ( UIModuleAnonymousDesktopInstance.getDesktopPosition() == DesktopPosition.Top ) {

    						UIModuleAnonymousDesktopInstance.getUIComponents( Executions.getCurrent(), TopHLayout, null );

    					}	
    					else if ( UIModuleAnonymousDesktopInstance.getDesktopPosition() == DesktopPosition.Left ) {

    						UIModuleAnonymousDesktopInstance.getUIComponents( Executions.getCurrent(), LeftVLayout, null );

    					}
    					else if ( UIModuleAnonymousDesktopInstance.getDesktopPosition() == DesktopPosition.Bottom ) {

    						UIModuleAnonymousDesktopInstance.getUIComponents( Executions.getCurrent(), BottomHLayout, null );

    					}
    					else if ( UIModuleAnonymousDesktopInstance.getDesktopPosition() == DesktopPosition.Right ) {

    						UIModuleAnonymousDesktopInstance.getUIComponents( Executions.getCurrent(), RightVLayout, null );

    					}
    					else if ( UIModuleAnonymousDesktopInstance.getDesktopPosition() == DesktopPosition.Center ) {   

    						UIModuleAnonymousDesktopInstance.getUIComponents( Executions.getCurrent(), InnerCenterLayout, null );

    					}
    					else if ( UIModuleAnonymousDesktopInstance.getDesktopPosition() == DesktopPosition.Overlap ) {

    						Component CreatedComponent = UIModuleAnonymousDesktopInstance.getUIComponents( Executions.getCurrent(), null, null );

    						if ( CreatedComponent != null ) {  

    							CreatedComponents.add( CreatedComponent );

    						}

    					}
    				
    				}
    				catch ( Exception Ex ) {
    					
    	    			System.out.println( Ex );
    					
    				}
        			
    			}
				
    			CreatedComponents.add( AnonymousDesktop );
    			
    		}
    		catch ( Exception Ex ) {
    			
    			System.out.println( Ex );
    			
    		}
            
		}
		else { //Logged desktop
			
    		try {

    			Component Desktop = Executions.getCurrent().createComponents( "~./CRichlet/uiviews/desktop.zul", null, null );

    			Borderlayout DesktopLayout = (Borderlayout) Desktop.getFellow( "DesktopLayout" );
    			
				if ( DesktopLayout != null ) {
					
					if ( Integer.parseInt( (String) session.getAttribute( "browserwidth" ) )  > 1024 )
					    DesktopLayout.setWidth( (String) session.getAttribute( "browserwidth" ) );

					if ( Integer.parseInt( (String) session.getAttribute( "browserheight" ) )  > 768 )
						DesktopLayout.setHeight( (String) session.getAttribute( "browserheight" ) );
				
				}
    			
				Hlayout TopHLayout = (Hlayout) Desktop.getFellowIfAny( "TopHLayout" );
				
				Vlayout LeftVLayout = (Vlayout) Desktop.getFellowIfAny( "LeftVLayout" );

				Hlayout BottomHLayout = (Hlayout) Desktop.getFellowIfAny( "BottomHLayout" );

				Vlayout RightVLayout = (Vlayout) Desktop.getFellowIfAny( "RightVLayout" );

				Vlayout InnerCenterLayout = (Vlayout) Desktop.getFellowIfAny( "InnerCenterLayout" );
    			
    			for ( int intIndexModule = 0; intIndexModule < RegisteredModulesUIDesktop.size(); intIndexModule++ ) {

    				try {
        				
    					CUIAbstractModuleDesktop UIModuleDesktopInstance = RegisteredModulesUIDesktop.get( intIndexModule );  

    					if ( UIModuleDesktopInstance.getDesktopPosition() == DesktopPosition.Top ) {

    						UIModuleDesktopInstance.getUIComponents( Executions.getCurrent(), TopHLayout, null );

    					}	
    					else if ( UIModuleDesktopInstance.getDesktopPosition() == DesktopPosition.Left ) {

    						UIModuleDesktopInstance.getUIComponents( Executions.getCurrent(), LeftVLayout, null );

    					}
    					else if ( UIModuleDesktopInstance.getDesktopPosition() == DesktopPosition.Bottom ) {

    						UIModuleDesktopInstance.getUIComponents( Executions.getCurrent(), BottomHLayout, null );

    					}
    					else if ( UIModuleDesktopInstance.getDesktopPosition() == DesktopPosition.Right ) {

    						UIModuleDesktopInstance.getUIComponents( Executions.getCurrent(), RightVLayout, null );

    					}
    					else if ( UIModuleDesktopInstance.getDesktopPosition() == DesktopPosition.Center ) {   

    						UIModuleDesktopInstance.getUIComponents( Executions.getCurrent(), InnerCenterLayout, null );

    					}
    					else if ( UIModuleDesktopInstance.getDesktopPosition() == DesktopPosition.Overlap ) {

    						Component CreatedComponent = UIModuleDesktopInstance.getUIComponents( Executions.getCurrent(), null, null );

    						if ( CreatedComponent != null ) {  

    							CreatedComponents.add( CreatedComponent );

    						}

    					}
    				
    				}
    				catch ( Exception Ex ) {
    					
    	    			System.out.println( Ex );
    					
    				}
        			
    			}
				
    			CreatedComponents.add( Desktop );
    			
    		}
    		catch ( Exception Ex ) {
    			
    			System.out.println( Ex );
    			
    		}
			
		}
    	
		for ( int intIndexComponent = 0; intIndexComponent < CreatedComponents.size(); intIndexComponent++ ) {
			
			CreatedComponents.get( intIndexComponent ).setPage( page );
			
		}
        
    }
	
    @Override
    public void init( RichletConfig config ) {

    	super.init( config );
    
		Session session = Sessions.getCurrent();

		String strJarFolder = session.getWebApp().getRealPath( "/" ); //Executions.getCurrent().getDesktop().getWebApp().getRealPath( "/" ); //servletContext.getRealPath("/"); //getJarFolder();*/

    	try {

			CClassPathLoader ClassPathLoader = new CClassPathLoader();

			ClassPathLoader.LoadClassFiles( strJarFolder + "/WEB-INF/modules/", ".jar", 2 );
    		
    	}
    	catch ( Exception Ex ) {

    		System.out.println( Ex );

    	}
    	
    }
 
    @Override
    public void destroy() {

    	super.destroy();
    	
    }
    
    public void LoadModules( final Desktop desktop, final Hbox ModulesLoaded, final Label lbProgressMessage, final Progressmeter ProgressBar ) {
    	
    	((DesktopCtrl)desktop).enableServerPush( new PollingServerPush( 1000, 2000, -1 ) );
    	
		final Session session = Sessions.getCurrent();

    	Thread ThreadLoadModules = new Thread() {
    		
    		public void LoadModulesUIAnonymousDesktop() {
    			
    			try {

    				Image OldImageModule = null;

    				if ( RegisteredModulesUIAnonymousDesktop == null ) {

    					RegisteredModulesUIAnonymousDesktop = new ArrayList<CUIAbstractModuleAnonymousDesktop>();

    					ServiceLoader<CUIAbstractModuleAnonymousDesktop> sl = ServiceLoader.load( CUIAbstractModuleAnonymousDesktop.class );
    					sl.reload();

    					Iterator<CUIAbstractModuleAnonymousDesktop> it = sl.iterator();

    					int intIndexModule = 0;

    					int intMaxModules = 0;

    					while ( it.hasNext() ) {

    						it.next();

    						intMaxModules += 1;

    					}

    					it = sl.iterator();

    					while ( it.hasNext() ) {

    						try {

    							CUIAbstractModuleAnonymousDesktop UIModuleAnonymousDesktopInstance = it.next();

    							Executions.activate( desktop );

    							lbProgressMessage.setValue( UIModuleAnonymousDesktopInstance.Translate( "Loading module" ) );

    							Executions.deactivate( desktop );

    							if ( UIModuleAnonymousDesktopInstance.InitModule() > 0 ) {

    								RegisteredModulesUIAnonymousDesktop.add( UIModuleAnonymousDesktopInstance );

    							}

    							Executions.activate( desktop );

    							Image ImageModule = new Image();

    							ImageModule.setSrc( UIModuleAnonymousDesktopInstance.getMainIconPath( "24x24" ) );

    							ModulesLoaded.insertBefore( ImageModule, OldImageModule );

    							OldImageModule = ImageModule;

    							lbProgressMessage.setValue( UIModuleAnonymousDesktopInstance.Translate( "Module loaded" ) );
    							ProgressBar.setValue( ( intIndexModule * 100 ) / intMaxModules );

    							intIndexModule += 1;

    							Executions.deactivate( desktop );

    						}
    						catch ( Exception Ex ) {

    							System.out.println( Ex );

    						}

    					}

    				}
    				else {

    					int intMaxModules = RegisteredModulesUIAnonymousDesktop.size();

    					for ( int intIndexModule = 0; intIndexModule < intMaxModules; intIndexModule++ ) {

    						try {

    							CUIAbstractModuleAnonymousDesktop UIModuleAnonymousDesktopInstance = RegisteredModulesUIAnonymousDesktop.get( intIndexModule );

    							Executions.activate( desktop );

    							Image ImageModule = new Image();

    							ImageModule.setSrc( UIModuleAnonymousDesktopInstance.getMainIconPath( "24x24" ) );

    							ModulesLoaded.insertBefore( ImageModule, OldImageModule );

    							OldImageModule = ImageModule;

    							lbProgressMessage.setValue( UIModuleAnonymousDesktopInstance.Translate( "Module loaded" ) );
    							ProgressBar.setValue( ( intIndexModule * 100 ) / intMaxModules );

    							Executions.deactivate( desktop );

    						}
    						catch ( Exception Ex ) {

    							System.out.println( Ex );

    						}

    					}

    				}

    			}
    			catch ( Exception Ex ) {

    				System.out.println( Ex );

    			}

    		}
    		
    		public void LoadModulesUIDesktop() {
    			
    			try {

    				Image OldImageModule = null;

    				if ( RegisteredModulesUIDesktop == null ) {

    					RegisteredModulesUIDesktop = new ArrayList<CUIAbstractModuleDesktop>();

    					ServiceLoader<CUIAbstractModuleDesktop> sl = ServiceLoader.load( CUIAbstractModuleDesktop.class );
    					sl.reload();

    					Iterator<CUIAbstractModuleDesktop> it = sl.iterator();

    					int intIndexModule = 0;

    					int intMaxModules = 0;

    					while ( it.hasNext() ) {

    						it.next();

    						intMaxModules += 1;

    					}

    					it = sl.iterator();

    					while ( it.hasNext() ) {

    						try {

    							CUIAbstractModuleDesktop UIModuleDesktopInstance = it.next();

    							Executions.activate( desktop );

    							lbProgressMessage.setValue( UIModuleDesktopInstance.Translate( "Loading module" ) );

    							Executions.deactivate( desktop );

    							if ( UIModuleDesktopInstance.InitModule() > 0 ) {

    								RegisteredModulesUIDesktop.add( UIModuleDesktopInstance );

    							}

    							Executions.activate( desktop );

    							Image ImageModule = new Image();

    							ImageModule.setSrc( UIModuleDesktopInstance.getMainIconPath( "24x24" ) );

    							ModulesLoaded.insertBefore( ImageModule, OldImageModule );

    							OldImageModule = ImageModule;

    							lbProgressMessage.setValue( UIModuleDesktopInstance.Translate( "Module loaded" ) );
    							ProgressBar.setValue( ( intIndexModule * 100 ) / intMaxModules );

    							intIndexModule += 1;

    							Executions.deactivate( desktop );

    						}
    						catch ( Exception Ex ) {

    							System.out.println( Ex );

    						}

    					}

    				}
    				else {

    					int intMaxModules = RegisteredModulesUIDesktop.size();

    					for ( int intIndexModule = 0; intIndexModule < intMaxModules; intIndexModule++ ) {

    						try {

    							CUIAbstractModuleDesktop UIModuleDesktopInstance = RegisteredModulesUIDesktop.get( intIndexModule );

    							Executions.activate( desktop );

    							Image ImageModule = new Image();

    							ImageModule.setSrc( UIModuleDesktopInstance.getMainIconPath( "24x24" ) );

    							ModulesLoaded.insertBefore( ImageModule, OldImageModule );

    							OldImageModule = ImageModule;

    							lbProgressMessage.setValue( UIModuleDesktopInstance.Translate( "Module loaded" ) );
    							ProgressBar.setValue( ( intIndexModule * 100 ) / intMaxModules );

    							Executions.deactivate( desktop );

    						}
    						catch ( Exception Ex ) {

    							System.out.println( Ex );

    						}

    					}

    				}

    			}
    			catch ( Exception Ex ) {

    				System.out.println( Ex );

    			}
    			
    		}	
    		
    		public void run() {
    		
    			try {

    				if ( desktop.isServerPushEnabled() ) {

        				this.LoadModulesUIAnonymousDesktop();
        				
        				this.LoadModulesUIDesktop();
        				
        				session.setAttribute( "app_initiated", "1" );

        				Executions.activate( desktop );
        				Executions.sendRedirect( null );
        				Executions.deactivate( desktop );
        				
    				}

    			}
    			catch ( Exception Ex ) {

					System.out.println( Ex );

    			}
    			finally {
    				
    				Executions.deactivate( desktop );
    				desktop.enableServerPush( false );
    				
    			}

    		}
    		
    	};
    	
    	ThreadLoadModules.start();
    	
    }
    
}