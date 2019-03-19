package model;

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

        System.out.println(cmd);
        cmd.Execute();
        undoStack.push(cmd);
        redoStack.clear();
        System.out.println(undoStack.size());


    }

    public void Undo()
    {
//        if (!undoStack.isEmpty()) return;
        if (!undoStack.isEmpty()) {
            System.out.println(undoStack);
            Command cmd = undoStack.pop();
            cmd.Undo();
            redoStack.push(cmd);
        }
    }

    public void Redo()
    {
        Command cmd = redoStack.pop();
        cmd.Redo();
        undoStack.push(cmd);
    }
}
