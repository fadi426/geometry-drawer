package model;

public class SingletonCmdMng {
    private static CommandManager instance;

    public static CommandManager getInstance() {
        if (instance == null){
            instance = new CommandManager();
        }
        return instance;
    }
}
