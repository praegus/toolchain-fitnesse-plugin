package nl.praegus.fitnesse;

import fitnesse.Responder;
import fitnesse.plugins.PluginException;
import fitnesse.plugins.PluginFeatureFactoryBase;
import fitnesse.responders.ResponderFactory;
import fitnesse.testsystems.slim.tables.SlimTable;
import fitnesse.testsystems.slim.tables.SlimTableFactory;
import nl.praegus.fitnesse.responders.AutoCompleteResponder;
import nl.praegus.fitnesse.responders.UpdateTagsResponder;
import nl.praegus.fitnesse.responders.TableOfContentsResponder;
import nl.praegus.fitnesse.slim.tables.ConditionalScriptTable;
import nl.praegus.fitnesse.slim.tables.PausingTable;

public class PraegusPluginFeatureFactory extends PluginFeatureFactoryBase {

    @Override
    public void registerSlimTables(SlimTableFactory slimTableFactory) throws PluginException {
        super.registerSlimTables(slimTableFactory);
        LOG.info("[Toolchain Plugin] Registering debug script table. Be sure to add 'pause test fixture' from nl.praegus:toolchain-fixtures as a library");
        add(slimTableFactory, "debug script", PausingTable.class);
        LOG.info("[Toolchain Plugin] Registering conditional script table.");
        add(slimTableFactory, "conditional script", ConditionalScriptTable.class);
    }

    private void add(SlimTableFactory factory, String key, Class<? extends SlimTable> tableType) {
        factory.addTableType(key, tableType);
    }

    @Override
    public void registerResponders(ResponderFactory responderFactory) throws PluginException {
        super.registerResponders(responderFactory);
        LOG.info("[Toolchain Plugin] Registering AutoCompleteResponder (?autoComplete).");
        add(responderFactory, "autoComplete", AutoCompleteResponder.class);
        LOG.info("[Toolchain Plugin] Registering TocResponder (?tableOfContents).");
        add(responderFactory, "tableOfContents", TableOfContentsResponder.class);
        LOG.info("[Toolchain Plugin] Registering UpdateTagsResponder (?updateTags).");
        add(responderFactory, "updateTags", UpdateTagsResponder.class);
    }

    private void add(ResponderFactory factory, String key, Class<? extends Responder> responder) {
        factory.addResponder(key, responder);
    }

    @Override
    public String getDefaultTheme() {
        LOG.info("[Toolchain Plugin] Changing theme to bootstrap-plus");
        return "bootstrap-plus";
    }
}
