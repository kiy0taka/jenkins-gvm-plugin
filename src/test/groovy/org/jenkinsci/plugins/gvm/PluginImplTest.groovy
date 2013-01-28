package org.jenkinsci.plugins.gvm

class PluginImplTest extends GroovyTestCase {

    PluginImpl plugin

    void setUp() throws Exception {
        super.setUp()
        plugin = new PluginImpl()
    }

    public void test_supports_correct_plugins() {
        assert PluginImpl.SUPPORTED_PLUGINS.keySet().size() == 3
        assert PluginImpl.SUPPORTED_PLUGINS.get('groovy') != null
        assert PluginImpl.SUPPORTED_PLUGINS.get('grails') != null
        assert PluginImpl.SUPPORTED_PLUGINS.get('gradle') != null
    }

    public void test_something() {
        assert true
    }
}
