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
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Timer;

public class CSplashScreenController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -4362626162184372513L;

	@Wire 
	Div BackgroundLayer;
	
	@Wire
	Image imgProductLogo;
	
	@Wire 
	Label lbProductName;
	
	@Wire 
	Label lbProductEdition;

	@Wire 
	Label lbProductTarget;
	
	@Wire 
	Label lbProductSlogan;
	
	@Wire
	Label lbProductVersion;
	
	@Wire 
	Hbox ModulesLoaded;
	
	@Wire 
    Label lbMessageProgress;
	
	@Wire 
	Progressmeter ProgressBar;

	@Wire
	Timer LoadTimer; 

	int intActualProgress = 0;
	
	int intMax = 15;
	
	Image OldImageModule = null;
	
	int intCountModule = 1;
	
	public void doAfterCompose( Component component ) throws Exception {

		super.doAfterCompose( component );

		//style="background-image: url(${c:encodeURL('~./CRichlet/images/product_splash_screen_01.png')}); background-repeat: no-repeat;"
		
		BackgroundLayer.setStyle( "background-image: url('" + Executions.getCurrent().encodeURL( (String) Executions.getCurrent().getArg().get( "background_image" ) ) + "'); background-repeat: no-repeat;" );
		
		imgProductLogo.setSrc( (String) Executions.getCurrent().getArg().get( "product_logo" ) );

		String strValue = (String) Executions.getCurrent().getArg().get( "product_name" );
		
		lbProductName.setValue( strValue );

		strValue = (String) Executions.getCurrent().getArg().get( "product_edition" );
		
		lbProductEdition.setValue( strValue );
		
		strValue = (String) Executions.getCurrent().getArg().get( "product_target" );
		
		lbProductTarget.setValue( strValue );
		
		strValue = (String) Executions.getCurrent().getArg().get( "product_slogan" );
		
		lbProductSlogan.setValue( strValue );
		
		strValue = (String) Executions.getCurrent().getArg().get( "product_version" );
		
		lbProductVersion.setValue( strValue );
		
	}

	@Listen( "onClientInfo = #SplashScreen")
	public void onClientInfo( ClientInfoEvent event ) {
		
		Session session = Sessions.getCurrent();
		
		session.setAttribute( "browserwidth", Integer.toString( event.getDesktopWidth() ) );
		session.setAttribute( "browserheight", Integer.toString( event.getDesktopHeight() ) );
		
	}
	
	@Listen( "onTimer = #LoadTimer" )
    public void onTimerLoad( Event event ) {
		
		intActualProgress += 1;
		
		if ( intActualProgress == intMax + 1 ) {
		
			LoadTimer.stop();
		
			Session session = Sessions.getCurrent();

			session.setAttribute( "app_initiated", "1" );

			Executions.sendRedirect( null );
			
		}   
		else {
		
			ProgressBar.setValue( ( intActualProgress * 100 ) / intMax );

			lbMessageProgress.setValue( "Loading module " + Integer.toString( intActualProgress ) + "..." );

			Image ImageModule = new Image();

			ImageModule.setSrc( "~./CRichlet/images/" + Integer.toString( intCountModule ) + ".png" );

			ModulesLoaded.insertBefore( ImageModule, OldImageModule );

			OldImageModule = ImageModule;

			intCountModule += 1;

			if ( intCountModule > 15 )
				intCountModule = 1; 
		
		}
		
	};
	
}