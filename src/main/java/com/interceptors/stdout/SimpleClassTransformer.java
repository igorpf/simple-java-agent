package com.interceptors.stdout;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class SimpleClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(
            final ClassLoader loader,
            final String className,
            final Class<?> classBeingRedefined,
            final ProtectionDomain protectionDomain,
            final byte[] classfileBuffer ) throws IllegalClassFormatException {

        if ("Main".equals(className)) {
            try {
                final ClassPool classPool = ClassPool.getDefault();
                final CtClass clazz =
                        classPool.get(className.replace("/", "."));

                CtMethod method = clazz.getDeclaredMethod("main");
                method.addCatch("{System.out.println(\"Suppressed exception\");return;}", classPool.get("java.lang.NullPointerException"));

                byte[] byteCode = clazz.toBytecode();
                clazz.detach();

                return byteCode;
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }

        return classfileBuffer;
    }
}
