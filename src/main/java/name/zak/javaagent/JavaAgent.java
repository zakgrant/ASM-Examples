package name.zak.javaagent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class JavaAgent {

    static final Logger logger = LoggerFactory.getLogger(JavaAgent.class);

    private static Instrumentation instrumentation;

    /**
     * JVM hook to statically load the javaagent at startup.
     * <p/>
     * After the Java Virtual Machine (JVM) has initialized, the premain method
     * will be called. Then the real application main method will be called.
     *
     * @param args
     * @param inst
     * @throws Exception
     */
    public static void premain(String args, Instrumentation inst) throws Exception {
        logger.info("premain method invoked with args: {} and inst: {}", args, inst);
        instrumentation = inst;
        instrumentation.addTransformer(new ClassFileTransformer());
    }

    /**
     * JVM hook to dynamically load javaagent at runtime.
     * <p/>
     * The agent class may have an agentmain method for use when the agent is
     * started after VM startup.
     *
     * @param args
     * @param inst
     * @throws Exception
     */
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        logger.info("agentmain method invoked with args: {} and inst: {}", args, inst);
        instrumentation = inst;
        instrumentation.addTransformer(new ClassFileTransformer());
    }

    /**
     * Programmatic hook to dynamically load javaagent at runtime.
     */
    public static void initialize() {
        if (instrumentation == null) {
            AgentLoader.loadAgent();
        }
    }

}