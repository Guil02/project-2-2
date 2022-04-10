//package group.seven.model;
//
//import group.seven.utils.Config;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.net.URISyntaxException;
//
//class ScenarioTest {
//
//    @Test
//    public void testScenario(){
//        String path = Config.DEFAULT_MAP_PATH;
//        Scenario.setPrintInput(true);
//
//        try {
//            Scenario scenario = new Scenario(new File(getClass().getResource(Config.DEFAULT_MAP_PATH).toURI()));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//}