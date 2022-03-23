package org.group7.agentVision;

import org.group7.enums.Actions;
import org.group7.model.GameRunner;
import org.group7.model.Grid;
import org.group7.model.Scenario;
import org.group7.model.algorithms.ActionTuple;
import org.group7.model.component.playerComponents.Guard;
import org.group7.model.geometric.SoundTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BasicVisionTest {
    @Test
    public void isHit(){
        File file = new File("/Users/mischatomaszrauch/UM/AcademicYear_02/Project2-2/test/project-2-2/src/main/resources/scenarios/debug.txt");
        Scenario test = new Scenario(file);
        //created a second constructor to check vision -- add it to run test while deleting all the gui elements in it
        //GameRunner gR = new GameRunner(test, true);

        // I know thats not how unit tests should look like but it was an easy and fast way to verify it
        Guard guard = test.getGuards().get(0);
        guard.applyAction(Actions.TURN_LEFT);
        guard.updateVision();
        System.out.println("ORIENTATION "+guard.getOrientation());
        System.out.println("VISION "+guard.getAgentsCurrentVision());
        guard.applyAction(Actions.TURN_UP);
        guard.updateVision();
        System.out.println("ORIENTATION "+guard.getOrientation());
        System.out.println("VISION "+guard.getAgentsCurrentVision());
        guard.applyAction(Actions.TURN_RIGHT);
        guard.updateVision();
        System.out.println("ORIENTATION "+guard.getOrientation());
        System.out.println("VISION "+guard.getAgentsCurrentVision());
        guard.applyAction(Actions.TURN_DOWN);
        guard.updateVision();
        System.out.println("ORIENTATION "+guard.getOrientation());
        System.out.println("VISION "+guard.getAgentsCurrentVision());
    }

}