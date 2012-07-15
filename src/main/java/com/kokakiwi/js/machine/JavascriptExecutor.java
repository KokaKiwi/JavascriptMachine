package com.kokakiwi.js.machine;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.kokakiwi.js.machine.sandbox.Sandboxer;

public class JavascriptExecutor implements Runnable
{
    private final JavascriptCore core;
    private final File           script;
    private Context              context;
    private Scriptable           scope;
    
    public JavascriptExecutor(JavascriptCore core, File script)
    {
        this(core, script, null, null);
    }
    
    public JavascriptExecutor(JavascriptCore core, File script,
            Context context, Scriptable scope)
    {
        this.core = core;
        this.script = script;
        this.context = context;
        this.scope = scope;
    }
    
    public JavascriptCore getCore()
    {
        return core;
    }
    
    public File getScript()
    {
        return script;
    }
    
    public Context getContext()
    {
        return context;
    }
    
    public Scriptable getScope()
    {
        return scope;
    }
    
    private void init()
    {
        if (context == null)
        {
            if (core.isSandboxed())
            {
                context = Sandboxer.getFactory().enterContext();
            }
            else
            {
                context = Context.enter();
            }
        }
        
        if (scope == null)
        {
            scope = context.initStandardObjects();
        }
        
        core.initApi(this);
    }
    
    public void run()
    {
        try
        {
            init();
            
            Reader reader = new FileReader(script);
            context.evaluateReader(scope, reader, "<cmd>", 1, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
