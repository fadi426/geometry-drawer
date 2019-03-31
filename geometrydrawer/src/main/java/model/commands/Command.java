package model.commands;

public interface Command {

    public void Execute();
    public void Undo();
    public void Redo();
}
