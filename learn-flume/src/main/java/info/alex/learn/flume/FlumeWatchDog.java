package info.alex.learn.flume;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.cloudera.flume.conf.FlumeConfiguration;

/**
 * This the program invoked for flume's watchdog. Basically invoke this program
 * with extra arguments that are the command to execute.
 * <p>
 * It may seem odd that this just has main. It was decided that it would be good
 * to have Flume in the command name. Except for one configuration file option,
 * the actual watchdog code is not dependent on flume, so it lives as a separate
 * class.
 */
public class FlumeWatchDog {

    static final Logger LOG = LoggerFactory.getLogger(FlumeWatchDog.class);

    public static void main(String[] argv) {
        if (argv.length == 0) {
            System.out.println("need to specify watched command as arguments");
            System.exit(-1);
        }

        // drop a pid file if these options are set.
        String pid = System.getProperty("pid");
        String pidfile = System.getProperty("pidfile");
        if (pidfile != null && pid != null) {
            File f = new File(pidfile);
            f.deleteOnExit();

            try {
                FileWriter fw = new FileWriter(f);
                fw.write(pid);
                fw.close();
            } catch (IOException e) {
                LOG.error("failed to drop a pid file", e);
                System.exit(-1);
            }
            LOG.info("Dropped a pidfile='" + pidfile + "' with pid=" + pid);
        } else {
            LOG.warn("No pid or pidfile system property specified.");
        }

        String interactiveprop = System.getProperty("fwdstdin");
        boolean interactive = (interactiveprop != null);

        String[] args = argv;

//        FlumeConfiguration conf = FlumeConfiguration.hardExitLoadConfig();
//        int maxTriesPerMin = conf.getMaxRestartsPerMin();
        int maxTriesPerMin = 10;

        WatchDog watchdog = new WatchDog(args, interactive);
        watchdog.run(maxTriesPerMin);
    }
}
