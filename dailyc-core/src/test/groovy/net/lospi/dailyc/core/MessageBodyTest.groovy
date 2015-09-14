package net.lospi.dailyc.core

import spock.lang.Specification

class MessageBodyTest extends Specification {
    def date = Mock(Date)

    def "can toString"() {
        setup:
        def messageBody = new MessageBody(contents: "message", lastUse: date)

        when:
        def result = messageBody.toString()

        then:
        result != null
    }
}
