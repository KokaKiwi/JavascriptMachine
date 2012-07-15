package com.kokakiwi.js.machine.sandbox;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

public class SandboxContextFactory extends ContextFactory
{
    @Override
    protected Context makeContext()
    {
        final Context cx = super.makeContext();
        
        cx.setWrapFactory(new SandboxWrapFactory());
        cx.setClassShutter(new SandboxClassShutter());
        
        return cx;
    }
}
