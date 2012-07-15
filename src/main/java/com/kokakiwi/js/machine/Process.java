package com.kokakiwi.js.machine;

public class Process implements Runnable
{
    private final int      pid;
    private final Runnable runnable;
    private boolean        running;
    
    public Process(int pid, Runnable runnable)
    {
        this.pid = pid;
        this.runnable = runnable;
    }
    
    public int getPid()
    {
        return pid;
    }
    
    public Runnable getRunnable()
    {
        return runnable;
    }
    
    public boolean isRunning()
    {
        return running;
    }
    
    public void run()
    {
        running = true;
        runnable.run();
        running = false;
    }
}
