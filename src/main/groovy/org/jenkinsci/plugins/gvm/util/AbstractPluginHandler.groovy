package org.jenkinsci.plugins.gvm.util

import hudson.FilePath

abstract class AbstractPluginHandler implements GvmCompatiblePluginHandler {

    protected abstract String getLabel()

    protected String getGvmCommand(String version) {
        """#!/usr/bin/env bash
source "${getHomeDir()}/.gvm/bin/gvm-init.sh"
yes | gvm install $label ${version != null ? version : ''}
gvm use $label
gvm default $label
gvm current $label
"""
    }

    protected void gvmShellInstall(String version = null) {
        FilePath dir = new FilePath(new File(System.getProperty('java.io.tmpdir')))
        FilePath script = dir.createTextTempFile("hudson", ".sh", getGvmCommand(version))
        try {
            "chmod +x ${script.getRemote()}".execute()
            def process = "${script.getRemote()}".execute()
            process.consumeProcessOutput()
        } finally {
            script.delete()
        }
    }

    protected String getGvmDir() {
        "${getHomeDir()}/.gvm/$label/current"
    }

    public static String getHomeDir() {
        System.getProperty('user.home')
    }
}
