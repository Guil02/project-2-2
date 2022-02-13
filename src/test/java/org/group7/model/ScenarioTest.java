package org.group7.model;

import org.group7.utils.Config;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    @Test
    public void testScenario(){
        String path = Config.DEFAULT_MAP_PATH;
        Scenario.setPrintInput(true);

        try {
            Scenario scenario = new Scenario(new File(getClass().getResource(Config.DEFAULT_MAP_PATH).toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}