package name.zak.javaagent;

import name.zak.user.SomeClass;
import org.objectweb.asm.util.ASMifierClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Runner {

    static final Logger logger = LoggerFactory.getLogger(Runner.class);

    static {
        JavaAgent.initialize();
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        logger.info("main method invoked with args: {}", Arrays.asList(args));
        logger.info("userName: {}", new SomeClass().getName());
        logger.info("value: {}", SomeClass.getValue());

//        ASMifierClassVisitor.main(new String[]{SomeClass.class.getName()});
    }

}