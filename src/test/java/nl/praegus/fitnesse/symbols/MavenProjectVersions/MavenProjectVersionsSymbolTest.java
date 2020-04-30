package nl.praegus.fitnesse.symbols.MavenProjectVersions;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenProjectVersionsSymbolTest {
    @Test
    public void compare_If_Current_SemanticVersion_Is_Outdated() {
        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
        String currentVersion = "1.1.0";
        String latestVersion = "1.2.0";

        String receivedValue = MavenVersionSymbol.getStatus(currentVersion, latestVersion);

        assertThat(receivedValue).isEqualTo("Outdated");
    }
//    @Test
//    public void test() throws FileNotFoundException {
////        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
////        ProjectDependencyInfo projectDependencyInfo = new ProjectDependencyInfo();
////        String receivedValue = MavenVersionSymbol.getVersionTableHtmlAsString(projectDependencyInfo.getDependencyInfo());
//
////        assertThat(receivedValue).isEqualTo("Outdated");
//    }
//
//    @Test
//    public void testing_toTarget() {
//        MavenProjectVersionsSymbol MavenVersionSymbol = new MavenProjectVersionsSymbol();
//        TestWikiPageDummyMavenVersions TestWikiPageDummyMavenVersions = new TestWikiPageDummyMavenVersions("dummyPage", "", new WikiPageDummy(),new WikiPageProperty());
//        WikiSourcePage testWikiSourcePage = new WikiSourcePage(TestWikiPageDummyMavenVersions);
//        Symbol symbol = new Symbol(new SymbolType("dwa"), "adaw", 1, 1);
//        Translator translator = new Translator(testWikiSourcePage, symbol);
//        System.out.println(MavenVersionSymbol.toTarget(translator, symbol);
//
//    }
//    @Test
//    public void tik_maar_effe() throws FileNotFoundException {
//        ProjectDependencyInfo projectDependencyInfo = new ProjectDependencyInfo();
//
//        assertThat(projectDependencyInfo).isEqualTo("Outdated");
//    }
}

