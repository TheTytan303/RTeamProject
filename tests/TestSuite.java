import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Test Suite")
@SelectClasses({GraphTest.class,
        RelationshipEdgeTest.class,
        RelationshipTest.class,
        RTeamTest.class,
        GitInfoTest.class,
        UserInterfaceTest.class,
        ParserTest.class,
        ExportTest.class})
public class TestSuite {
}