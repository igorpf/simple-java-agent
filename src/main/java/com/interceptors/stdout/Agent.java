package com.interceptors.stdout;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) throws IOException {
        System.out.println("Starting agent on premain");
        inst.addTransformer(new SimpleClassTransformer(), true);
    }
    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {
        System.out.println("Starting agent on agentmain");
        inst.addTransformer(new SimpleClassTransformer());
    }

}