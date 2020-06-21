import java.util.Scanner;
import java.util.HashMap;
import java.util.Random;
import java.io.*;

public class ScenarioFile {

    private static Random randGenerator;

    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 60;

    private Integer regionWidth;
    private Integer regionHeight;
    private Integer[][] regionInfo;
    private Integer numberOfDrones;
    private Integer droneX[], droneY[];
    private String droneDirection[];
    private Integer droneStrategy[];
    private Integer droneStatus[];
    private HashMap<String, Integer> xDIR_MAP;
    private HashMap<String, Integer> yDIR_MAP;


    private Integer turnLimit;

    public static final int EMPTY_CODE = 0;
    public static final int STARS_CODE = 1;
    public static final int SUN_CODE = 2;

    private final int MAX_DRONES = 2;
    private final int OK_CODE = 1;
    private final int CRASH_CODE = -1;



    public ScenarioFile() {
        randGenerator = new Random();

        regionHeight = 0;
        regionWidth = 0;
        regionInfo = new Integer[DEFAULT_WIDTH][DEFAULT_HEIGHT];

        numberOfDrones = -1;
        droneX = new Integer[MAX_DRONES];
        droneY = new Integer[MAX_DRONES];
        droneDirection = new String[MAX_DRONES];
        droneStrategy = new Integer[MAX_DRONES];
        droneStatus = new Integer[MAX_DRONES];

        for(int k = 0; k < MAX_DRONES; k++) {
            droneX[k] = -1;
            droneY[k] = -1;
            droneDirection[k] = "north";
            droneStrategy[k] = -1;
            droneStatus[k] = CRASH_CODE;
        }

        xDIR_MAP = new HashMap<>();
        xDIR_MAP.put("north", 0);
        xDIR_MAP.put("northeast", 1);
        xDIR_MAP.put("east", 1);
        xDIR_MAP.put("southeast", 1);
        xDIR_MAP.put("south", 0);
        xDIR_MAP.put("southwest", -1);
        xDIR_MAP.put("west", -1);
        xDIR_MAP.put("northwest", -1);

        yDIR_MAP = new HashMap<>();
        yDIR_MAP.put("north", 1);
        yDIR_MAP.put("northeast", 1);
        yDIR_MAP.put("east", 0);
        yDIR_MAP.put("southeast", -1);
        yDIR_MAP.put("south", -1);
        yDIR_MAP.put("southwest", -1);
        yDIR_MAP.put("west", 0);
        yDIR_MAP.put("northwest", 1);

        turnLimit = -1;
    }

    public void uploadStartingFile(String testFileName) {
        final String DELIMITER = ",";

        try {
            Scanner takeCommand = new Scanner(new File(testFileName));
            String[] tokens;
            int i, j, k;

            // read in the region information
            tokens = takeCommand.nextLine().split(DELIMITER);
            regionWidth = Integer.parseInt(tokens[0]);
            tokens = takeCommand.nextLine().split(DELIMITER);
            regionHeight = Integer.parseInt(tokens[0]);

            // generate the region information
            regionInfo = new Integer[regionWidth][regionHeight];
            for (i = 0; i < regionWidth; i++) {
                for (j = 0; j < regionHeight; j++) {
                    regionInfo[i][j] = STARS_CODE;
                }
            }

            // read in the drone starting information
            tokens = takeCommand.nextLine().split(DELIMITER);
            numberOfDrones = Integer.parseInt(tokens[0]);
            for (k = 0; k < numberOfDrones; k++) {
                tokens = takeCommand.nextLine().split(DELIMITER);
                droneX[k] = Integer.parseInt(tokens[0]);
                droneY[k] = Integer.parseInt(tokens[1]);
                droneDirection[k] = tokens[2];
                droneStrategy[k] = Integer.parseInt(tokens[3]);
                droneStatus[k] = OK_CODE;

                // explore the stars at the initial location
                regionInfo[droneX[k]][droneY[k]] = EMPTY_CODE;
            }

            // read in the sun information
            tokens = takeCommand.nextLine().split(DELIMITER);
            int numSuns = Integer.parseInt(tokens[0]);
            for (k = 0; k < numSuns; k++) {
                tokens = takeCommand.nextLine().split(DELIMITER);

                // place a sun at the given location
                regionInfo[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])] = SUN_CODE;
            }

            tokens = takeCommand.nextLine().split(DELIMITER);
            turnLimit = Integer.parseInt(tokens[0]);

            takeCommand.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }










    public int getDefaultWidth(){
        return this.DEFAULT_WIDTH;
    }

    public int getDefaultHeight(){
        return this.DEFAULT_HEIGHT;
    }

    public static Random getRandGenerator() {
        return randGenerator;
    }

    public Integer getRegionWidth(){
        return this.regionWidth;
    }

    public Integer getRegionHeight(){
        return this.regionHeight;
    }

    public Integer[][] getRegionInfo(){
        return this.regionInfo;
    }

    public Integer getNumberOfDrones(){
        return this.numberOfDrones;
    }

    public Integer[] getDroneX(){
        return this.droneX;
    }

    public Integer[] getDroneY(){
        return this.droneY;
    }

    public String[] getDroneDirection(){
        return this.droneDirection;
    }

    public Integer[] getDroneStrategy(){
        return this.droneStrategy;
    }

    public Integer[] getDroneStatus(){
        return this.droneStatus;
    }

    public HashMap<String, Integer> getxDIR_MAP(){
        return this.xDIR_MAP;
    }

    public HashMap<String, Integer> getyDIR_MAP(){
        return this.yDIR_MAP;
    }

    public Integer getTurnLimit(){
        return this.turnLimit;
    }

    public int getMAX_DRONES(){
        return this.MAX_DRONES;
    }

    public int getCRASH_CODE(){
        return this.CRASH_CODE;
    }

    public int getOK_CODE(){
        return this.OK_CODE;
    }

    public int getEMPTY_CODE(){
        return this.EMPTY_CODE;
    }

    public int getSTARS_CODE(){
        return this.STARS_CODE;
    }

    public int getSUN_CODE(){
        return this.SUN_CODE;
    }


}
