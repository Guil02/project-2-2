//package group.seven.agentVision;
//
//import group.seven.enums.Actions;
//import group.seven.model.component.playerComponents.Guard;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//
//class BasicVisionTest {
//    @Test
//    public void isHit(){
//        File file = new File("/Users/mischatomaszrauch/UM/AcademicYear_02/Project2-2/test/project-2-2/src/main/resources/scenarios/debug.txt");
//        Scenario test = new Scenario(file);
//        //created a second constructor to check vision -- add it to run test while deleting all the gui elements in it
//        //GameRunner gR = new GameRunner(test, true);
//
//        // I know thats not how unit tests should look like but it was an easy and fast way to verify it
//        Guard guard = test.getGuards().get(0);
//        guard.applyAction(Actions.TURN_LEFT);
//        guard.updateVision();
//        System.out.println("ORIENTATION "+guard.getOrientation());
//        System.out.println("VISION "+guard.getAgentsCurrentVision());
//        guard.applyAction(Actions.TURN_UP);
//        guard.updateVision();
//        System.out.println("ORIENTATION "+guard.getOrientation());
//        System.out.println("VISION "+guard.getAgentsCurrentVision());
//        guard.applyAction(Actions.TURN_RIGHT);
//        guard.updateVision();
//        System.out.println("ORIENTATION "+guard.getOrientation());
//        System.out.println("VISION "+guard.getAgentsCurrentVision());
//        guard.applyAction(Actions.TURN_DOWN);
//        guard.updateVision();
//        System.out.println("ORIENTATION "+guard.getOrientation());
//        System.out.println("VISION "+guard.getAgentsCurrentVision());
//    }
//
//}