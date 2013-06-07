package AbstractModules;

import java.util.ArrayList;

public interface IAbstractModule {
 
	public String getModuleName( String strLanguageName );
    public String getModuleNameGUID();
	public String getModuleInternalName();
    public String getModuleDescription( String strLanguageName );
    public String getModuleVersion();

    public ArrayList<CModuleDependencies> getModuleDependencies();
    
    public int InitModule();    

    public boolean ModuleActivated();    
	
}
