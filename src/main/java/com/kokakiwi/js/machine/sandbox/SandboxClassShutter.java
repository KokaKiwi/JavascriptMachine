package com.kokakiwi.js.machine.sandbox;

import org.mozilla.javascript.ClassShutter;

public class SandboxClassShutter implements ClassShutter
{
    public boolean visibleToScripts(String className)
    {
        if (className.startsWith("adapter"))
        {
            return true;
        }
        else if (className.startsWith("com.kokakiwi.bukkit.jsbukkit.js.api"))
        {
            
        }
        
        return false;
    }
}
