package arch.seven.utils;

import static org.group7.utils.Methods.print;

public class Methods {
    public static void main(String[] args) {
        print("gameFile = /Users/joel/Documents/gamefile.txt".replaceAll("([\s]*(//))(.)*", ""));
        print("spawnAreaGuards = 2 2 20 10 // Area where guards spawn -> big enough to contain number guards".replaceAll("([\s]*(//))(.)*", ""));

    }
}
