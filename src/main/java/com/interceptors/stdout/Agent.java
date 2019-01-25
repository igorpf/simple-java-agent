package com.interceptors.stdout;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) throws IOException {
        System.out.println("Starting agent on premain");
        addInterceptor(inst);
    }
    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {
        System.out.println("Starting agent on agentmain");
        addInterceptor(inst);
    }

    private static void addInterceptor(Instrumentation instrumentation) {
        StdoutInterceptor interceptor = new StdoutInterceptor();
        interceptor.intercept(instrumentation);
    }
}