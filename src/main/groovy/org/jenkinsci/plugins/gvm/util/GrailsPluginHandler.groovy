package org.jenkinsci.plugins.gvm.util

import com.g2one.hudson.grails.GrailsInstallation
import jenkins.model.Jenkins

import java.util.logging.Logger

class GrailsPluginHandler extends AbstractPluginHandler {

    private static final Logger LOG = Logger.getLogger(GrailsPluginHandler.class.getName())

    @Override
    protected String getLabel() { return 'grails' }

    Boolean handlesPluginType(Class klass) {
        klass == com.g2one.hudson.grails.GrailsInstallation.class
    }

    public void createOrUpdateGvmInstall() {
        gvmShellInstall()
        def gvmDir = getGvmDir()
        LOG.fine("Grails Home Directory: ${gvmDir}")
        GrailsInstallation grailsInstallation = new GrailsInstallation('GRAILS_GVM', gvmDir, [])
        def installations = Jenkins.getInstance().getDescriptorByType(GrailsInstallation.DescriptorImpl.class).getInstallations()
        def newInstallations = [grailsInstallation]
        installations.each { install ->
            newInstallations.add(install)
        }

        def installArray = newInstallations.toArray() as GrailsInstallation[]

        Jenkins.getInstance().getDescriptorByType(GrailsInstallation.DescriptorImpl).setInstallations(installArray)
    }
}
