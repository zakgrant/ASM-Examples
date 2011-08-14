package name.zak.javaagent;

import com.sun.tools.attach.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

public class AgentLoader {

    static final Logger logger = LoggerFactory.getLogger(AgentLoader.class);

    private static final String jarFilePath = "/Users/zak/.m2/repository/"
            + "javaagent-examples/javaagent-examples/1.0/"
            + "javaagent-examples-1.0-jar-with-dependencies.jar";

    public static void loadAgent() {
        logger.info("dynamically loading javaagent");
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);

        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(jarFilePath, "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}