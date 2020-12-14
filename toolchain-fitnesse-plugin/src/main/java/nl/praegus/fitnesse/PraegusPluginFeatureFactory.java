package nl.praegus.fitnesse;

import fitnesse.Responder;
import fitnesse.plugins.PluginException;
import fitnesse.plugins.PluginFeatureFactoryBase;
import fitnesse.responders.ResponderFactory;
import fitnesse.testsystems.slim.tables.SlimTable;
import fitnesse.testsystems.slim.tables.SlimTableFactory;
import fitnesse.wikitext.parser.SymbolProvider;
import fitnesse.wikitext.parser.SymbolType;
import fitnesse.wikitext.parser.Table;
import nl.praegus.fitnesse.decorators.TableSymbolDecorator;
import nl.praegus.fitnesse.responders.AutoCompleteResponder;
import nl.praegus.fitnesse.responders.TableOfContentsResponder;
import nl.praegus.fitnesse.responders.ToolTip.TooltipResponder;
import nl.praegus.fitnesse.responders.UpdateTagsResponder;
import nl.praegus.fitnesse.responders.allTags.AllTagsResponder;
import nl.praegus.fitnesse.responders.symbolicLink.SymbolicLinkResponder;
import nl.praegus.fitnesse.responders.testHistory.RecentTestHistoryResponder;
import nl.praegus.fitnesse.slim.tables.ConditionalScenarioTable;
import nl.praegus.fitnesse.slim.tables.ConditionalScriptTable;
import nl.praegus.fitnesse.slim.tables.LoopingScenarioTable;
import nl.praegus.fitnesse.slim.tables.PausingTable;
import nl.praegus.fitnesse.symbols.Fake;
import nl.praegus.fitnesse.symbols.IncludeIfAvailable;
import nl.praegus.fitnesse.symbols.MavenProjectVersions.MavenProjectVersionsSymbol;

public class PraegusPluginFeatureFactory extends PluginFeatureFactoryBase {

    @Override
    public void registerSlimTables(SlimTableFactory slimTableFactory) throws PluginException {
        super.registerSlimTables(slimTableFactory);
        LOG.info("[Toolchain Plugin] Registering debug script table. Be sure to add 'pause test fixture' from nl.praegus:toolchain-fixtures as a library");
        add(slimTableFactory, "debug script", PausingTable.class);
        LOG.info("[Toolchain Plugin] Registering conditional script table.");
        add(slimTableFactory, "conditional script", ConditionalScriptTable.class);
        LOG.info("[Toolchain Plugin] Registering conditional scenario table.");
        add(slimTableFactory, "conditional scenario", ConditionalScenarioTable.class);
        LOG.info("[Toolchain Plugin] Registering Looping scenario table.");
        add(slimTableFactory, "looping scenario", LoopingScenarioTable.class);

    }

    private void add(SlimTableFactory factory, String key, Class<? extends SlimTable> tableType) {
        factory.addTableType(key, tableType);
    }

    @Override
    public void registerSymbolTypes(SymbolProvider symbolProvider) throws PluginException {
        super.registerSymbolTypes(symbolProvider);

        LOG.info("[Toolchain Plugin] Registering table specific decorator classes.");
        TableSymbolDecorator.install(Table.symbolType);
        add(symbolProvider, new MavenProjectVersionsSymbol());
        add(symbolProvider, new IncludeIfAvailable());
        add(symbolProvider, new Fake());
    }

    private void add(SymbolProvider provider, SymbolType symbolType) {
        provider.add(symbolType);
        LOG.info("[Toolchain Plugin] Added symbol " + symbolType.getClass());
    }

    @Override
    public void registerResponders(ResponderFactory responderFactory) throws PluginException {
        super.registerResponders(responderFactory);
        LOG.info("[Toolchain Plugin] Registering Toolchain plugin responders.");
        add(responderFactory, "autoComplete", AutoCompleteResponder.class);
        add(responderFactory, "tableOfContents", TableOfContentsResponder.class);
        add(responderFactory, "updateTags", UpdateTagsResponder.class);
        add(responderFactory, "recentTestHistory", RecentTestHistoryResponder.class);
        add(responderFactory, "allTags", AllTagsResponder.class);
        add(responderFactory, "symlinks", SymbolicLinkResponder.class);
        add(responderFactory, "Tooltips", TooltipResponder.class);
    }

    private void add(ResponderFactory factory, String key, Class<? extends Responder> responder) {
        factory.addResponder(key, responder);
    }

    @Override
    public String getDefaultTheme() {
        LOG.info("[Toolchain Plugin] Setting theme to bootstrap-plus");
        return "bootstrap-plus";
    }
}
