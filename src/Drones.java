import java.util.HashMap;

public class Drones {
    public String scanAroundSquare(int targetX, int targetY) {

        ScenarioFile sFile = new ScenarioFile();
        Integer[] droneX = sFile.getDroneX();
        Integer[] droneY = sFile.getDroneY();
        int OK_CODE = sFile.getOK_CODE();
        int regionWidth = sFile.getRegionWidth();
        int regionHeight = sFile.getRegionHeight();
        Integer[] droneStatus = sFile.getDroneStatus();
        Integer[][] regionInfo = sFile.getRegionInfo();
        HashMap<String, Integer> xDIR_MAP = sFile.getxDIR_MAP();
        HashMap<String, Integer> yDIR_MAP = sFile.getyDIR_MAP();

        SimManager simManager = new SimManager();
        String[] ORIENT_LIST = simManager.getORIENT_LIST();

        String nextSquare, resultString = "";

        for (int k = 0; k < ORIENT_LIST.length; k++) {
            String lookThisWay = ORIENT_LIST[k];
            int offsetX = xDIR_MAP.get(lookThisWay);
            int offsetY = yDIR_MAP.get(lookThisWay);

            int checkX = targetX + offsetX;
            int checkY = targetY + offsetY;

            if (checkX < 0 || checkX >= regionWidth || checkY < 0 || checkY >= regionHeight) {
                nextSquare = "barrier";
            } else if (droneStatus[0] == OK_CODE && checkX == droneX[0] && checkY == droneY[0]) {
                nextSquare = "drone";
            } else if (droneStatus[1] == OK_CODE && checkX == droneX[1] && checkY == droneY[1]) {
                nextSquare = "drone";
            } else {
                switch (regionInfo[checkX][checkY]) {
                    case ScenarioFile.EMPTY_CODE:
                        nextSquare = "empty";
                        break;
                    case ScenarioFile.STARS_CODE:
                        nextSquare = "stars";
                        break;
                    case ScenarioFile.SUN_CODE:
                        nextSquare = "sun";
                        break;
                    default:
                        nextSquare = "unknown";
                        break;
                }
            }

            if (resultString.isEmpty()) { resultString = nextSquare; }
            else { resultString = resultString + "," + nextSquare; }
        }

        return resultString;
    }
}
