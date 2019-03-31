package model.commands;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public CommandManager(){
        undoStack = new Stack();
        redoStack = new Stack();
    }

    public void Execute(Command cmd)
    {
        cmd.Execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void Undo()
    {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.Undo();
            redoStack.push(cmd);
        }
    }

    public void Redo()
    {
        if (redoStack.size() == 0) {
            System.out.println("Redostack is empty, returning");
            return;
        }

        Command cmd = redoStack.pop();
        cmd.Redo();
        undoStack.push(cmd);
    }
}
