package com.kokakiwi.js.machine.example;

import java.io.File;

import com.kokakiwi.js.machine.JavascriptCore;
import com.kokakiwi.js.machine.api.impl.CoreApi;
import com.kokakiwi.js.machine.api.impl.UtilsApi;

public class JavascriptExample
{
    public JavascriptExample()
    {
        File scriptDir = new File("js");
        File main = new File(scriptDir, "main.js");
        
        JavascriptCore core = new JavascriptCore(true);
        
        core.registerApi(new CoreApi(core, scriptDir));
        core.registerApi(new UtilsApi());
        
        core.execute(main);
    }
    
    public static void main(String[] args)
    {
        new JavascriptExample();
    }
    
}
