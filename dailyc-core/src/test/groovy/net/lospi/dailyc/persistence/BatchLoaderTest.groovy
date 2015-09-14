package net.lospi.dailyc.persistence

import groovy.json.JsonSlurper
import spock.lang.Specification

class BatchLoaderTest extends Specification {
    File config
    JsonSlurper slurper

    void setup() {
        config = new File(getClass().getResource("/config.json").toURI())
        slurper = new JsonSlurper()
    }

    def "can get jpegs from directory"() {
        setup:
        def batchLoader = new BatchLoader(configFile: config, slurper: slurper)

        when:
        def jpegs = batchLoader.jpegs

        then:
        jpegs.containsAll([new File("build\\resources\\test\\img\\image-1.jpg"),
                           new File("build\\resources\\test\\img\\image-2.jpg"),
                           new File("build\\resources\\test\\img\\image-3.jpg")])
    }

    def "can get messages from config"() {
        setup:
        def batchLoader = new BatchLoader(configFile: config, slurper: slurper)

        when:
        def messages = batchLoader.messages

        then:
        messages.containsAll(["hello", "world", "foo bar bas"])
    }

    def "can get subscribers from config"() {
        setup:
        def batchLoader = new BatchLoader(configFile: config, slurper: slurper)

        when:
        def subscribers = batchLoader.subscribers

        then:
        subscribers.containsAll(["1111111111", "1111111112", "1111111113"])
    }
}
