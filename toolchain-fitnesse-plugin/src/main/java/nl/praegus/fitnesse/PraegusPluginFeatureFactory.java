package nl.praegus.fitnesse;

import fitnesse.Responder;
import fitnesse.plugins.PluginException;
import fitnesse.plugins.PluginFeatureFactoryBase;
import fitnesse.responders.ResponderFactory;
import fitnesse.testsystems.slim.tables.SlimTable;
import fitnesse.testsystems.slim.tables.SlimTableFactory;
import fitnesse.wikitext.parser.SymbolProvider;
import fitnesse.wikitext.parser.SymbolType;
import nl.praegus.fitnesse.decorators.TableSymbolDecorator;
import nl.praegus.fitnesse.responders.AutoCompleteResponder;
import nl.praegus.fitnesse.responders.UpdateTagsResponder;
import nl.praegus.fitnesse.responders.TableOfContentsResponder;
import nl.praegus.fitnesse.responders.symbolicLink.SymbolicLinkResponder;
import nl.praegus.fitnesse.responders.ToolTip.TooltipResponder;
import nl.praegus.fitnesse.responders.testHistory.RecentTestHistoryResponder;
import nl.praegus.fitnesse.responders.allTags.AllTagsResponder;
import nl.praegus.fitnesse.slim.tables.ConditionalScenarioTable;
import nl.praegus.fitnesse.slim.tables.ConditionalScriptTable;
import nl.praegus.fitnesse.slim.tables.LoopingScenarioTable;
import nl.praegus.fitnesse.slim.tables.PausingTable;
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
        LOG.info("[Toolchain Plugin] Registering table specific css decorator classes.");
        TableSymbolDecorator.install();
        super.registerSymbolTypes(symbolProvider);
        add(symbolProvider, new MavenProjectVersionsSymbol());
    }

    private void add(SymbolProvider provider, SymbolType symbolType) {
        provider.add(symbolType);
        LOG.info("Added symbol " + symbolType.getClass());
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
        LOG.info("[Toolchain Plugin] Registering TestRecentHistoryResponder (?recentTestHistory).");
        add(responderFactory, "recentTestHistory", RecentTestHistoryResponder.class);
        LOG.info("[Toolchain Plugin] Registering AllTagsResponder (?allTags).");
        add(responderFactory, "allTags", AllTagsResponder.class);
        LOG.info("[Toolchain Plugin] Registering SymbolicLinkResponder (?symlinks).");
        add(responderFactory, "symlinks", SymbolicLinkResponder.class);
        LOG.info("[Toolchain Plugin] Registering TooltipResponder (?Tooltips).");
        add(responderFactory, "Tooltips", TooltipResponder.class);
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
