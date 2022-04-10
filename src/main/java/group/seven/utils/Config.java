package group.seven.utils;

import group.seven.enums.AlgorithmEnum;

public class Config {
    public static final String DEFAULT_MAP_PATH = "/scenarios/biggerTestMap.txt";

    public static final double DEFAULT_VIEW_FIELD_ANGLE = Math.toRadians(20);

    public static final double DEFAULT_SOUND_DISTANCE = 50;
    public static final double TIME_STEP = 0.1;
    public static final boolean DEBUG_MODE = true;
    public static boolean LOG_DATA = false;
    public static AlgorithmEnum ALGORITHM = AlgorithmEnum.A_STAR;

}
