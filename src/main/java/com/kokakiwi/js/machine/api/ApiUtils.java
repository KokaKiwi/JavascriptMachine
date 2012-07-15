package com.kokakiwi.js.machine.api;

import java.lang.reflect.Method;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

import com.kokakiwi.js.machine.JavascriptExecutor;

public class ApiUtils
{
    public static void registerObject(JavascriptExecutor e, String name,
            Object obj)
    {
        Object jsObject = Context.javaToJS(obj, e.getScope());
        ScriptableObject.putProperty(e.getScope(), name, jsObject);
    }
    
    public static void registerFunction(JavascriptExecutor e, String name,
            Class<?> clazz, String methodName, Class<?>[] paramsTypes)
    {
        registerFunction(e, name, clazz, methodName, paramsTypes, null);
    }
    
    public static void registerFunction(JavascriptExecutor e, String name,
            Class<?> clazz, String methodName, Class<?>[] paramsTypes,
            Object parent)
    {
        registerFunction(e, name, clazz, methodName, paramsTypes, parent, true);
    }
    
    public static void registerFunction(JavascriptExecutor e, String name,
            Class<?> clazz, String methodName, Class<?>[] paramsTypes,
            Object parent, boolean withContext)
    {
        JavaFunction function = new JavaFunction(clazz, methodName,
                paramsTypes, parent, withContext);
        registerObject(e, name, function);
    }
    
    public static class JavaFunction extends BaseFunction
    {
        private static final long serialVersionUID = 6551932071225202369L;
        
        private final Class<?>    clazz;
        private final String      methodName;
        private final Class<?>[]  paramsTypes;
        private final Object      parent;
        private final boolean     withContext;
        
        public JavaFunction(Class<?> clazz, String methodName,
                Class<?>[] paramsTypes, Object parent, boolean withContext)
        {
            this.clazz = clazz;
            this.methodName = methodName;
            this.paramsTypes = paramsTypes;
            this.parent = parent;
            this.withContext = withContext;
        }
        
        public Class<?> getClazz()
        {
            return clazz;
        }
        
        public String getMethodName()
        {
            return methodName;
        }
        
        public Class<?>[] getParamsTypes()
        {
            return paramsTypes;
        }
        
        public Object getParent()
        {
            return parent;
        }
        
        @Override
        public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                Object[] args)
        {
            Object result = Undefined.instance;
            
            try
            {
                Class<?>[] callArgsTypes;
                
                if (withContext)
                {
                    callArgsTypes = new Class<?>[paramsTypes.length + 3];
                    callArgsTypes[0] = Context.class;
                    callArgsTypes[1] = Scriptable.class;
                    callArgsTypes[2] = Scriptable.class;
                    System.arraycopy(paramsTypes, 0, callArgsTypes, 3,
                            paramsTypes.length);
                }
                else
                {
                    callArgsTypes = paramsTypes;
                }
                
                Method method = clazz.getMethod(methodName, callArgsTypes);
                
                Object[] callArgs;
                
                if (withContext)
                {
                    callArgs = new Object[args.length + 3];
                    callArgs[0] = cx;
                    callArgs[1] = scope;
                    callArgs[2] = thisObj;
                    System.arraycopy(args, 0, callArgs, 3, args.length);
                }
                else
                {
                    callArgs = args;
                }
                
                result = Context.javaToJS(method.invoke(parent, callArgs),
                        scope);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            return result;
        }
    }
}
