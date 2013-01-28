package org.jenkinsci.plugins.gvm.util;

import hudson.tools.ToolInstallation;

public interface GvmCompatiblePluginHandler {

    public Boolean handlesPluginType(Class klass);

    public void createOrUpdateGvmInstall();

}
