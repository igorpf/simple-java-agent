package com.interceptors.stdout;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.PrintStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public class StdoutInterceptor {

    public void intercept(Instrumentation instrumentation) {
        try {
            System.out.println("Patching java.io.PrintStream#println methods");

            CtClass ctClass = ClassPool.getDefault().getCtClass(PrintStream.class.getName());
            for(CtMethod println : ctClass.getDeclaredMethods("println")){
                System.out.println(println.getSignature());
                println.instrument(new ExprEditor() {
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getMethodName().equals("print")) {
                            m.replace("{print(\"println has been intercepted!\");}");
                        }
                    }
                });
            }

            System.out.println("Finished patching java.io.PrintStream#println methods");
            instrumentation.redefineClasses(new ClassDefinition(PrintStream.class, ctClass.toBytecode()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
