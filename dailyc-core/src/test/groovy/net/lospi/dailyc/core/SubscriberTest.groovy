package net.lospi.dailyc.core

import spock.lang.Specification

class SubscriberTest extends Specification {
    def "can toString"() {
        setup:
        def subscriber = new Subscriber(number: "12345", lastUse: Mock(Date))

        when:
        def result = subscriber.toString()

        then:
        result != null
    }
}
