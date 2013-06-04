package ZKRichlet;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Timer;

public class CSplashScreenController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -4362626162184372513L;

	/*public void doAfterCompose( Component component ) throws Exception {

		super.doAfterCompose( component );
		
	}*/

	int intProgress = 0;
	
	int intMax = 15;
	
	Image OldImageModule = null;
	
	int intCountModule = 1;
	
	@Wire
	Timer LoadTimer; 
	
	@Wire 
    Label lbMessageProgress;
	
	@Wire 
	Progressmeter ProgressBar;
	
	@Wire
	Hbox ModulesLoaded;
	
	@Listen( "onClientInfo = #SplashScreen")
	public void onClientInfo( ClientInfoEvent event ) {
		
		Session session = Sessions.getCurrent();
		
		session.setAttribute( "browserwidth", Integer.toString( event.getDesktopWidth() ) );
		session.setAttribute( "browserheight", Integer.toString( event.getDesktopHeight() ) );
		
	}
	
	@Listen( "onTimer = #LoadTimer" )
    public void onTimerLoad( Event event ) {
		
		//int intProgress = ProgressBar.getValue();
		
		intProgress += 1;
		
		if ( intProgress == intMax ) {
		
			LoadTimer.stop();
		
			Session session = Sessions.getCurrent();

			session.setAttribute( "app_initiated", "1" );

			Executions.sendRedirect( null );
			
		}   

		ProgressBar.setValue( ( intProgress * 100 ) / intMax );
		
		lbMessageProgress.setValue( "Loading module " + Integer.toString( intProgress ) + "..." );
		
		Image ImageModule = new Image();
		
		ImageModule.setSrc( "~./CRichlet/images/" + Integer.toString( intCountModule ) + ".png" );

		ModulesLoaded.insertBefore( ImageModule, OldImageModule );

		OldImageModule = ImageModule;
		
		intCountModule += 1;
		
		if ( intCountModule > 15 )
			intCountModule = 1; 
		
	}
	
	
}