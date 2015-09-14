package net.lospi.dailyc.comm

import groovy.util.logging.Slf4j
import net.lospi.dailyc.core.MessageBody
import net.lospi.dailyc.core.Subscriber
import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.SendRequest

@Slf4j
class MogreetSender {
    MogreetManager manager
    String campaignId, from

    def send(Subscriber subscriber, MessageBody messageBody, String contentId) {
        log.info("[ ] Sending content {} to number {}; message: {}", contentId, subscriber, messageBody)
        def request = new SendRequest(campaignId, subscriber.number, from, messageBody.contents, contentId)
        def result = manager.send(request)
        if(result.successful)
            log.info("[+] Success! Response from Mogreet: {}", result.messageResponse)
        else
            log.error("[-] Failure! Response from Mogreet: %n{}", result.xmlResponse)
        return result
    }
}
