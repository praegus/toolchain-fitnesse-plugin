package nl.praegus.fitnesse;

import com.github.tcnh.fitnesse.responders.AutoCompleteResponder;
import fitnesse.Responder;
import fitnesse.plugins.PluginException;
import fitnesse.plugins.PluginFeatureFactoryBase;
import fitnesse.responders.ResponderFactory;
import fitnesse.testsystems.slim.tables.SlimTable;
import fitnesse.testsystems.slim.tables.SlimTableFactory;
import nl.praegus.fitnesse.slim.tables.PausingTable;

public class PraegusPluginFeatureFactory extends PluginFeatureFactoryBase {

    @Override
    public void registerSlimTables(SlimTableFactory slimTableFactory) throws PluginException {
        super.registerSlimTables(slimTableFactory);
        add(slimTableFactory, "debug script", PausingTable.class);
    }

    private void add(SlimTableFactory factory, String key, Class<? extends SlimTable> tableType) {
        factory.addTableType(key, tableType);
        LOG.info("Added Slim table type: " + key + ": " + tableType.getName());
    }

    @Override
    public void registerResponders(ResponderFactory responderFactory) throws PluginException {
        super.registerResponders(responderFactory);
        add(responderFactory, "autoComplete", AutoCompleteResponder.class);
    }

    private void add(ResponderFactory factory, String key, Class<? extends Responder> responder) {
        factory.addResponder(key, responder);
        LOG.info("Autoloaded responder " + key + ": " + responder.getName());
    }

}
