package model.commands;

public interface Command {

    /**
     * Execute the given command
     */
    void Execute();

    /**
     * Undo the given command
     */
    void Undo();

    /**
     * Redo the given command
     */
    void Redo();
}
