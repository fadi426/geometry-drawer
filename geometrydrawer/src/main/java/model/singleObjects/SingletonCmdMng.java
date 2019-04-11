package model.singleObjects;

import model.commands.CommandManager;

public class SingletonCmdMng {
    private static CommandManager instance;

    /**
     * Returns the same instance of the CommandManager
     * @return Returns the CommandManager.
     */
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }
}
