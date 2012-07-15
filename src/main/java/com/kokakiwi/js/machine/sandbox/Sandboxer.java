package com.kokakiwi.js.machine.sandbox;

import org.mozilla.javascript.ContextFactory;

public class Sandboxer
{
    public static ContextFactory getFactory()
    {
        final ContextFactory factory = new SandboxContextFactory();
        
        return factory;
    }
}
