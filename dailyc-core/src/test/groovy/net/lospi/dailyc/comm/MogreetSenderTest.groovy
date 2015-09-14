package net.lospi.dailyc.comm

import net.lospi.dailyc.core.MessageBody
import net.lospi.dailyc.core.Subscriber
import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.SendResponse
import spock.lang.Specification

class MogreetSenderTest extends Specification {
    def subscriber = Mock(Subscriber)
    def messageBody = Mock(MessageBody)
    def campaignId = "23456"
    def fromNumber = "12345"
    def toNumber = "34567"
    def message = "hello world"
    def contentId = "45678"
    def manager = Mock(MogreetManager)
    def expectedResponse = Mock(SendResponse)

    def "can send content"() {
        setup:
        subscriber.number >> toNumber
        messageBody.contents >> message
        manager.send({ r -> r.to == toNumber &&
                r.from == fromNumber &&
                r.message == message &&
                r.contentId == contentId &&
                r.campaignId == campaignId }) >> expectedResponse
        expectedResponse.successful >> true

        def sender = new MogreetSender(campaignId: campaignId, manager: manager, from: fromNumber)

        when:
        def result = sender.send(subscriber, messageBody, contentId)

        then:
        assert result == expectedResponse
    }

    def "graceful when unsuccessful"() {
        setup:
        subscriber.number >> toNumber
        messageBody.contents >> message
        manager.send({ r -> r.to == toNumber &&
                r.from == fromNumber &&
                r.message == message &&
                r.contentId == contentId &&
                r.campaignId == campaignId }) >> expectedResponse
        expectedResponse.successful >> false

        def sender = new MogreetSender(campaignId: campaignId, manager: manager, from: fromNumber)

        when:
        def result = sender.send(subscriber, messageBody, contentId)

        then:
        assert result == expectedResponse
    }
}