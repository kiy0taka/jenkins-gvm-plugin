package org.jenkinsci.plugins.gvm.util

import hudson.plugins.gradle.GradleInstallation
import jenkins.model.Jenkins

import java.util.logging.Logger

class GradlePluginHandler extends AbstractPluginHandler {

    private static final Logger LOG = Logger.getLogger(GradlePluginHandler.class.getName())

    @Override
    protected String getLabel() {
        'gradle'
    }

    Boolean handlesPluginType(Class klass) {
        klass == hudson.plugins.gradle.GradleInstallation.class
    }

    public void createOrUpdateGvmInstall() {
        gvmShellInstall()
        def gvmDir = getGvmDir()
        LOG.fine("Gradle Home Directory: ${gvmDir}")
        GradleInstallation grailsInstallation = new GradleInstallation('GRADLE_GVM', gvmDir, [])
        def installations = Jenkins.getInstance().getDescriptorByType(GradleInstallation.DescriptorImpl.class).getInstallations()
        def newInstallations = [grailsInstallation]
        installations.each { install ->
            newInstallations.add(install)
        }

        def installArray = newInstallations.toArray() as GradleInstallation[]

        Jenkins.getInstance().getDescriptorByType(GradleInstallation.DescriptorImpl).setInstallations(installArray)
    }
}
