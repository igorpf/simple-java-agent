# Simple Java Agent

Sample functional Java Agent that logs names of classes being loaded by JVM.

In the different branches there are different samples of Java agents functionalities.

Usage example (assuming a compiled Main Java class and the .jar file in the same directory):

```$ java -javaagent:stdout-interceptor-1.0.jar Main```

## Generating a jar file

Being on the project root directory, run:

```$ ./gradlew jar```

The jar will be generated at build/libs directory.
