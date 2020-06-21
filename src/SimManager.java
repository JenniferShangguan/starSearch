import java.util.Scanner;
import java.util.HashMap;
import java.util.Random;
import java.io.*;

public class SimManager {

    private String trackAction;
    private String trackNewDirection;
    private Integer trackThrustDistance;
    private String trackMoveCheck;
    private String trackScanResults;
    private final String[] ORIENT_LIST = {"north", "northeast", "east", "southeast", "south", "southwest", "west", "northwest"};

    ScenarioFile sFile = new ScenarioFile();
    Integer[] droneStrategy = sFile.getDroneStrategy();
    Random randGenerator = sFile.getRandGenerator();
    Integer[] droneX = sFile.getDroneX();
    Integer[] droneY = sFile.getDroneY();
    int MAX_DRONES = sFile.getMAX_DRONES();
    int OK_CODE = sFile.getOK_CODE();
    int CRASH_CODE = sFile.getCRASH_CODE();
    int regionWidth = sFile.getRegionWidth();
    int regionHeight = sFile.getRegionHeight();
    Integer[] droneStatus = sFile.getDroneStatus();
    Integer[][] regionInfo = sFile.getRegionInfo();
    String[] droneDirection = sFile.getDroneDirection();
    HashMap<String, Integer> xDIR_MAP = sFile.getxDIR_MAP();
    HashMap<String, Integer> yDIR_MAP = sFile.getyDIR_MAP();

    Drones drones = new Drones();

    public void pollDroneForAction(int id) {
        int moveRandomChoice, thrustRandomChoice, steerRandomChoice;

        if (droneStrategy[id] == 2) {
            Scanner askUser = new Scanner(System.in);
            // generate a move by asking the user - DIAGNOSTIC ONLY
            System.out.print("action?: ");
            trackAction = askUser.nextLine();

            if (trackAction.equals("steer")) {
                System.out.print("direction?: ");
                trackNewDirection = askUser.nextLine();
            } else if (trackAction.equals("thrust")) {
                System.out.print("distance?: ");
                trackThrustDistance = Integer.parseInt(askUser.nextLine());
            }

        } else {
            // generate a move randomly
            moveRandomChoice = randGenerator.nextInt(100);
            if (moveRandomChoice < 5) {
                // do nothing
                trackAction = "pass";
            } else if (moveRandomChoice < 20) {
                // check your surroundings
                trackAction = "scan";
            } else if (moveRandomChoice < 50) {
                // change direction
                trackAction = "steer";
            } else {
                // thrust forward
                trackAction = "thrust";
                thrustRandomChoice = randGenerator.nextInt(3);
                trackThrustDistance = thrustRandomChoice + 1;
            }

            // determine a new direction
            steerRandomChoice = randGenerator.nextInt(8);
            if (trackAction.equals("steer")) {
                trackNewDirection = ORIENT_LIST[steerRandomChoice];
            }
        }
    }

    public void validateDroneAction(int id) {
        int xOrientation, yOrientation;

        if (trackAction.equals("scan")) {
            // in the case of a scan, return the information for the eight surrounding squares
            // always use a northbound orientation
            trackScanResults = drones.scanAroundSquare(droneX[id], droneY[id]);
            trackMoveCheck = "ok";

        } else if (trackAction.equals("pass")) {
            trackMoveCheck = "ok";

        } else if (trackAction.equals("steer")) {
            droneDirection[id] = trackNewDirection;
            trackMoveCheck = "ok";

        } else if (trackAction.equals("thrust")) {
            // in the case of a thrust, ensure that the move doesn't cross suns or barriers
            xOrientation = xDIR_MAP.get(droneDirection[id]);
            yOrientation = yDIR_MAP.get(droneDirection[id]);

            trackMoveCheck = "ok";
            int remainingThrust = trackThrustDistance;

            while (remainingThrust > 0 && trackMoveCheck.equals("ok")) {

                int newSquareX = droneX[id] + xOrientation;
                int newSquareY = droneY[id] + yOrientation;

                 //drone hit the barrier and wrap around!
                if (newSquareX < 0){
                    newSquareX = regionWidth;
                } else if (newSquareX >= regionWidth){
                    newSquareX = 0;
                } else if (newSquareY < 0){
                    newSquareY = regionHeight;
                } else if (newSquareY >= regionHeight){
                    newSquareY = 0;
                } else if (regionInfo[newSquareX][newSquareY] == sFile.SUN_CODE) {
                    // drone hit a sun
                    droneStatus[id] = CRASH_CODE;
                    trackMoveCheck = "crash";

                } else if (newSquareX == droneX[1 - id] && newSquareY == droneY[1 - id]) {
                    // drone collided with the other drone
                    droneStatus[id] = CRASH_CODE;
                    droneStatus[1 - id] = CRASH_CODE;
                    trackMoveCheck = "crash";

                } else {
                    // drone thrust is successful
                    droneX[id] = newSquareX;
                    droneY[id] = newSquareY;
                    // update region status
                    regionInfo[newSquareX][newSquareY] = sFile.EMPTY_CODE;
                }

                remainingThrust = remainingThrust - 1;
            }

        } else {
            // in the case of an unknown action, treat the action as a pass
            trackMoveCheck = "action_not_recognized";
        }
    }

    public void displayActionAndResponses(int id) {
        // display the drone's actions
        System.out.print("d" + String.valueOf(id) + "," + trackAction);
        if (trackAction.equals("steer")) {
            System.out.println("," + trackNewDirection);
        } else if (trackAction.equals("thrust")) {
            System.out.println("," + trackThrustDistance);
        } else {
            System.out.println();
        }

        // display the simulation checks and/or responses
        if (trackAction.equals("thrust") || trackAction.equals("steer") || trackAction.equals("pass")) {
            System.out.println(trackMoveCheck);
        } else if (trackAction.equals("scan")) {
            System.out.println(trackScanResults);
        } else {
            System.out.println("action_not_recognized");
        }
    }

    public String[] getORIENT_LIST(){
        return this.ORIENT_LIST;
    }

}
