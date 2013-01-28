package org.jenkinsci.plugins.gvm

import hudson.Plugin
import jenkins.model.Jenkins
import org.jenkinsci.plugins.gvm.util.AbstractPluginHandler

import java.util.logging.Logger

public class PluginImpl extends Plugin {

    private static final Logger LOG = Logger.getLogger(PluginImpl.class.getName())

    private static final Map<String, Class> SUPPORTED_PLUGINS = [
            'groovy' : hudson.plugins.groovy.GroovyInstallation.class,
            'gradle' : hudson.plugins.gradle.GradleInstallation.class,
            'grails': com.g2one.hudson.grails.GrailsInstallation.class
    ]

    @Override
    public void start() throws Exception {
        LOG.info('GVM Plugin Started')
    }

    @Override
    public void postInitialize() throws Exception {
        Jenkins jenkins = Jenkins.getInstance()
        for (pluginKey in SUPPORTED_PLUGINS.keySet()) {
            def plugin = jenkins.pluginManager.getPlugin(pluginKey)
            if (!plugin.isActive() || !plugin.isEnabled()) {
                break
            }

            def installationClass = SUPPORTED_PLUGINS[pluginKey]
            def descriptor = jenkins.getDescriptor(installationClass)
            def installations = descriptor.getInstallations()
            boolean needsInstall = true
            for (install in installations) {
                if (install.name.contains('_GVM')) {
                    needsInstall = false
                }
            }

            if (needsInstall) {
                LOG.info("Updating GVM for $pluginKey")
                SupportedPluginFactory.getHandlerForPlugin(installationClass)?.createOrUpdateGvmInstall()
            } else {
                LOG.info("Plugin ${pluginKey} already setup for GVM access")
            }
        }
    }

    // TODO: Doesn't seem to do anything, is the feature broken?
    private void updateGvmConfig() {
        def gvmDir = "${AbstractPluginHandler.getHomeDir()}/.gvm"
        def configFile = new File("${gvmDir}/etc/config")
        configFile.withPrintWriter { pw ->
            pw << 'gvm_auto_answer=true    #used to make gvm non-interactive, great for CI environments.\n'
        }
    }
}
