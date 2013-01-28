package org.jenkinsci.plugins.gvm

import org.jenkinsci.plugins.gvm.util.GradlePluginHandler
import org.jenkinsci.plugins.gvm.util.GrailsPluginHandler
import org.jenkinsci.plugins.gvm.util.GroovyPluginHandler
import org.jenkinsci.plugins.gvm.util.GvmCompatiblePluginHandler

import java.util.*

class SupportedPluginFactory {

    private static final List<GvmCompatiblePluginHandler> GVM_HANDLERS

    static {
        List<GvmCompatiblePluginHandler> tempList = new ArrayList<GvmCompatiblePluginHandler>()
        tempList.add(new GroovyPluginHandler())
        tempList.add(new GrailsPluginHandler())
        tempList.add(new GradlePluginHandler())
        GVM_HANDLERS = Collections.unmodifiableList(tempList)
    }

    public static GvmCompatiblePluginHandler getHandlerForPlugin(Class toolInstallationClass) {
        for(GvmCompatiblePluginHandler handler : GVM_HANDLERS) {
            if(handler.handlesPluginType(toolInstallationClass))
                return handler
        }

        return null
    }

}
