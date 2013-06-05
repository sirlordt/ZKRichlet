package AbstractModules;

import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;

public abstract class CUIAbstractModule implements IAbstractModule {

	public enum DesktopPosition { Top, Left, Bottom, Right, Center, Overlap };
	
    public abstract Component getUIComponents( Execution CurrentExecution, Component Parent, @SuppressWarnings("rawtypes") HashMap Args );

    public abstract String getIconPath( String strSize );
    
    public abstract DesktopPosition getDesktopPosition();
    
}
