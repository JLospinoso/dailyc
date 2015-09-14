package net.lospi.dailyc.comm

import groovy.util.logging.Slf4j
import net.lospi.dailyc.core.Subscriber
import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.SubscribeRequest
import org.springframework.beans.factory.annotation.Autowired

import static net.lospi.mogreet.util.MogreetConstants.USER_SETOPT_OPTIN

@Slf4j
class MogreetSubscriber {
    MogreetManager manager
    String campaignId

    def subscribe(Subscriber subscriber) {
        log.info("[ ] Subscribing number {}", subscriber)
        def request = new SubscribeRequest(subscriber.number, campaignId, USER_SETOPT_OPTIN)
        def result = manager.subscribe(request);
        if(result.successful)
            log.info("[+] Success! Response from Mogreet: {}", result.messageResponse)
        else
            log.error("[-] Failure! Response from Mogreet: %n{}", result.xmlResponse)
        return result
    }
}
