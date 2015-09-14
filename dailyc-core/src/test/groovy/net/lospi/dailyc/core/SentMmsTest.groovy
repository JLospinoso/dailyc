package net.lospi.dailyc.core

import spock.lang.Specification

class SentMmsTest extends Specification {
    def "can toString"() {
        setup:
        def sentMms = new SentMms(messageBody: Mock(MessageBody), subscriber: Mock(Subscriber),
                imageFile: Mock(ImageFile), sentDate: Mock(Date))

        when:
        def result = sentMms.toString()

        then:
        result != null
    }
}
