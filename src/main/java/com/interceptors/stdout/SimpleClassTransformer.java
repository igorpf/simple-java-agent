package com.interceptors.stdout;

import javassist.*;
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
        try {
            final ClassPool classPool = ClassPool.getDefault();
            final CtClass clazz = classPool.get(className.replace("/", "."));

            for (final CtConstructor constructor: clazz.getConstructors()) {
                constructor.insertAfter("System.out.println(\"Creating a new instance of: \" + this.getClass().getName());");
            }

            byte[] byteCode = clazz.toBytecode();
            clazz.detach();

            return byteCode;

        } catch (final Exception ex) {
            ex.printStackTrace();
        }

        return classfileBuffer;
    }
}
