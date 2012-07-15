package com.kokakiwi.js.machine.api.impl;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import com.kokakiwi.js.machine.JavascriptCore;
import com.kokakiwi.js.machine.JavascriptExecutor;
import com.kokakiwi.js.machine.api.ApiUtils;
import com.kokakiwi.js.machine.api.JavascriptApi;

public class UtilsApi implements JavascriptApi
{
    public void init(JavascriptCore core, JavascriptExecutor executor)
    {
        ApiUtils.registerFunction(executor, "stop", UtilsApi.class, "stop",
                new Class<?>[0], null, false);
        ApiUtils.registerFunction(executor, "echo", UtilsApi.class, "echo",
                new Class<?>[] { String.class }, null, false);
        ApiUtils.registerFunction(executor, "wait", UtilsApi.class, "wait",
                new Class<?>[] { long.class, Function.class });
        ApiUtils.registerFunction(executor, "sleep", UtilsApi.class, "sleep",
                new Class<?>[] { long.class }, null, false);
    }
    
    public static void stop()
    {
        System.exit(0);
    }
    
    public static void echo(String message)
    {
        System.out.println(message);
    }
    
    public static void sleep(long delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void wait(Context cx, Scriptable scope, Scriptable thisObj,
            long delay, Function callback)
    {
        try
        {
            Thread.sleep(delay);
            
            if (callback != null)
            {
                callback.call(cx, scope, thisObj, new Object[0]);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
