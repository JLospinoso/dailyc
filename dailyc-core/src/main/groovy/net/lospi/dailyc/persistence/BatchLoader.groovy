package net.lospi.dailyc.persistence

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

@Slf4j
class BatchLoader {
    JsonSlurper slurper
    File configFile

    List<File> getJpegs() {
        getKeyFile("imageDirectory").listFiles().findAll{it.isFile() && it.absolutePath.endsWith("jpg")}
    }

    List<String> getMessages() {
        (List<String>) getKey("messages")
    }

    List<String> getSubscribers() {
        (List<String>) getKey("subscribers")
    }

    private File getKeyFile(String key) {
        String path = getKey(key)
        File file = new File(path)
        if (!file.exists())
            throw new RuntimeException(String.format("File does not exist: %s", file.absolutePath))
        file
    }

    private def getKey(String key) {
        def config = parseConfig()
        verifyConfigContainsKey(config, key)
        config[key]
    }

    private Map<String, ?> parseConfig() {
        if (!configFile.exists())
            throw new RuntimeException(String.format("File does not exist: %s", configFile.absolutePath))
        (Map<String, ?>) slurper.parse(configFile)
    }

    private void verifyConfigContainsKey(Map<String, ?> config, String key) {
        if (!config.containsKey(key)) {
            log.error("[-] Unknown file contents:\n{}", config.toString())
            throw new RuntimeException(String.format("File does not contain key %s: %s", key, configFile.absolutePath))
        }
    }
}
