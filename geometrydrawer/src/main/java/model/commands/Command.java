package model.commands;

public interface Command {

    void Execute();

    void Undo();

    void Redo();
}
