package group.seven.utils;

import group.seven.enums.AlgorithmType;
import group.seven.enums.GameMode;

public class Config {
    public static final String DEFAULT_MAP_PATH = "/scenarios/biggerTestMap.txt";
    public static final String DEFAULT_MAP_PATH_GUI_OFF = "scenarios/biggerTestMap.txt";

    public static final double DEFAULT_VIEW_FIELD_ANGLE = Math.toRadians(20);

    public static final double DEFAULT_SOUND_DISTANCE = 50;
    public static final double TIME_STEP = 0.1;
    public static final boolean DEBUG_MODE = false;
    public static boolean LOG_DATA = false;
    public static AlgorithmType ALGORITHM_GUARD = AlgorithmType.RANDOM;
    public static AlgorithmType ALGORITHM_INTRUDER = AlgorithmType.A_STAR;

    public static boolean GUI_ON = true;
    public static boolean GA_ON = true;

    public static int MAX_GAME_LENGTH = 1000;
    public static GameMode GAMEMODE;

}
