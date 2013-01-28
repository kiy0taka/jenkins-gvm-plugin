package org.jenkinsci.plugins.gvm.util

import hudson.FilePath
import hudson.plugins.groovy.Groovy
import hudson.plugins.groovy.GroovyInstallation
import jenkins.model.Jenkins

import java.util.logging.Logger

public class GroovyPluginHandler extends AbstractPluginHandler {

    private static final Logger LOG = Logger.getLogger(GroovyPluginHandler.class.getName())

    @Override
    protected String getLabel() { return 'groovy' }

    public Boolean handlesPluginType(Class klass) {
        klass == hudson.plugins.groovy.GroovyInstallation.class
    }

    public void createOrUpdateGvmInstall() {
        gvmShellInstall()
        def gvmDir = getGvmDir()
        LOG.fine("Groovy Home Directory: ${gvmDir}")
        GroovyInstallation gvmGroovyInstallation = new GroovyInstallation('GROOVY_GVM', gvmDir, [])
        def installations = Jenkins.getInstance().getDescriptorByType(Groovy.DescriptorImpl.class).getInstallations()
        def newInstallations = [gvmGroovyInstallation]
        installations.each { install ->
            newInstallations.add(install)
        }

        def installArray = newInstallations.toArray() as GroovyInstallation[]

        Jenkins.getInstance().getDescriptorByType(Groovy.DescriptorImpl.class).setInstallations(installArray)
    }
}
