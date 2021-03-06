/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package tech.romashov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private Logger log;

    public String getGreeting() {
        log.info("Invoke greeting");
        return "Hello world.";
    }

    public Application() {
        log = LoggerFactory.getLogger(Application.class);
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.getGreeting();
    }
}
