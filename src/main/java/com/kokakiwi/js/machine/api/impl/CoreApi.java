package com.kokakiwi.js.machine.api.impl;

import java.io.File;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

import com.kokakiwi.js.machine.JavascriptCore;
import com.kokakiwi.js.machine.JavascriptExecutor;
import com.kokakiwi.js.machine.api.ApiUtils;
import com.kokakiwi.js.machine.api.JavascriptApi;

public class CoreApi implements JavascriptApi
{
    private final JavascriptCore core;
    private final File           scriptDir;
    
    public CoreApi(JavascriptCore core, File scriptDir)
    {
        this.core = core;
        this.scriptDir = scriptDir;
    }
    
    public void init(JavascriptCore core, JavascriptExecutor executor)
    {
        ApiUtils.registerFunction(executor, "require", CoreApi.class,
                "require", new Class<?>[] { String.class }, this, true);
    }
    
    public Object require(Context cx, Scriptable scope, Scriptable thisObj,
            String name)
    {
        Object obj = Undefined.instance;
        
        File script = new File(scriptDir, name + ".js");
        JavascriptExecutor executor = new JavascriptExecutor(core, script, cx,
                scope);
        executor.run();
        
        obj = executor.getScope().get("exports", scope);
        
        return obj;
    }
}
