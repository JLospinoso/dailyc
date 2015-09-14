package net.lospi.dailyc.comm

import net.lospi.dailyc.comm.MogreetSubscriber
import net.lospi.dailyc.core.Subscriber
import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.BaseResponse
import spock.lang.Specification

class MogreetSubscriberTest extends Specification {
    def campaignId = "12334"
    def number = "3174556774"
    Subscriber subscriber = Mock(Subscriber)

    def "can subscribe a number"() {
        setup:
        subscriber.number >> number
        def manager = Mock(MogreetManager)
        def expectedResponse = Mock(BaseResponse)
        manager.subscribe({ r -> r.number == number &&
                                 r.campaignId == campaignId}) >> expectedResponse
        expectedResponse.successful >> true

        def mogreetSubscriber = new MogreetSubscriber(campaignId: campaignId, manager: manager)

        when:
        def result = mogreetSubscriber.subscribe(subscriber)

        then:
        assert result == expectedResponse
    }

    def "graceful when unsuccessful"() {
        setup:
        subscriber.number >> number
        def manager = Mock(MogreetManager)
        def expectedResponse = Mock(BaseResponse)
        manager.subscribe({ r -> r.number == number &&
                r.campaignId == campaignId}) >> expectedResponse
        expectedResponse.successful >> false

        def mogreetSubscriber = new MogreetSubscriber(campaignId: campaignId, manager: manager)

        when:
        def result = mogreetSubscriber.subscribe(subscriber)

        then:
        assert result == expectedResponse
    }
}