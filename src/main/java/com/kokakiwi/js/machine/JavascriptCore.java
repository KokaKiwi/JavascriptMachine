package com.kokakiwi.js.machine;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.js.machine.api.JavascriptApi;

public class JavascriptCore
{
    private final ProcessManager      processManager = new ProcessManager();
    private final List<JavascriptApi> apis           = Lists.newLinkedList();
    
    private final boolean sandboxed;
    
    public JavascriptCore(boolean sandboxed)
    {
        this.sandboxed = sandboxed;
    }
    
    public ProcessManager getProcessManager()
    {
        return processManager;
    }
    
    public void registerApi(JavascriptApi api)
    {
        apis.add(api);
    }
    
    public List<JavascriptApi> getApis()
    {
        return apis;
    }
    
    public boolean isSandboxed()
    {
        return sandboxed;
    }
    
    public void execute(File script)
    {
        JavascriptExecutor executor = new JavascriptExecutor(this, script);
        Process process = processManager.createProcess(executor);
        processManager.execute(process);
    }
    
    public void initApi(JavascriptExecutor executor)
    {
        for (JavascriptApi api : apis)
        {
            api.init(this, executor);
        }
    }
}
