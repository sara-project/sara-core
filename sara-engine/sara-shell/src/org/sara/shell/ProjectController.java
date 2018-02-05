package org.sara.shell;

import org.sara.interfaces.IPlugin;
import org.sara.interfaces.IProjectController;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectController extends IProjectController {

    @Override
    public void addNameActivePlugin( String name ) {
        nameActivePlugins.add( name );
    }

    @Override
    public List<String> allActivePlugin() {
        return nameActivePlugins;
    }

    @Override
    public List<IPlugin> pluginsByType( Object type ) {
        List<String> pluginsNames = this.allActivePlugin();
        List<IPlugin> plugins = new ArrayList<>();

        for (int i = 0; i < pluginsNames.size(); i++) {
            IPlugin temp;
            try {
                temp = (IPlugin) Class.forName( pluginsNames.get( i ), true, dataJars ).newInstance();

                if (type.getClass().isInstance( temp )) {
                    plugins.add( temp );
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger( ProjectController.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }

        return plugins;
    }

    @Override
    public void setDataJars( URLClassLoader dataJars ) {
        this.dataJars = dataJars;
    }

    private URLClassLoader dataJars;
}
