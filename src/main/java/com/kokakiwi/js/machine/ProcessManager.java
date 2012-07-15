package com.kokakiwi.js.machine;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Maps;

public class ProcessManager
{
    private final Map<Integer, Process> processes = Maps.newLinkedHashMap();
    private final ExecutorService       executor  = Executors
                                                          .newCachedThreadPool();
    
    private int genPid()
    {
        int pid;
        Random random = new Random();
        
        do
        {
            pid = random.nextInt(2000);
        }
        while (getProcess(pid) != null);
        
        return pid;
    }
    
    public Process createProcess(Runnable runnable)
    {
        Process process = new Process(genPid(), runnable);
        
        processes.put(process.getPid(), process);
        
        return process;
    }
    
    public Process getProcess(int pid)
    {
        Process process = processes.get(pid);
        
        return process;
    }
    
    public Map<Integer, Process> getProcesses()
    {
        return processes;
    }
    
    public Collection<Process> getProcessesList()
    {
        return processes.values();
    }
    
    public ExecutorService getExecutor()
    {
        return executor;
    }
    
    public void execute(Process process)
    {
        executor.execute(process);
    }
}
