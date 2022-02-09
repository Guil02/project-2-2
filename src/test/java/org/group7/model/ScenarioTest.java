package org.group7.model;

import org.group7.utils.Config;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    @Test
    public void testScenario(){
        String path = Config.DEFAULT_MAP_PATH;
        Scenario.setPrintInput(true);
        Scenario scenario = new Scenario(getClass().getResource(path).getFile());
    }

}