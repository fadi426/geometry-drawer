package model.commands;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public CommandManager() {
        undoStack = new Stack();
        redoStack = new Stack();
    }

    /**
     * Executes a given command
     * @param cmd The command you want to execute
     */
    public void Execute(Command cmd) {
        cmd.Execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    /**
     * Undoes the command on the undo stack and pushes it to the redostack
     */
    public void Undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.Undo();
            redoStack.push(cmd);
        }
    }

    /**
     * Redoes the command on the redo stack and pushes it back to the undo stack
     */
    public void Redo() {
        if (redoStack.size() == 0) {
            System.out.println("Redostack is empty, returning");
            return;
        }

        Command cmd = redoStack.pop();
        cmd.Redo();
        undoStack.push(cmd);
    }
}
