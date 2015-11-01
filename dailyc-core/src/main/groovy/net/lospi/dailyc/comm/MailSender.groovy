package net.lospi.dailyc.comm

import groovy.util.logging.Slf4j
import net.lospi.dailyc.core.EmailSubscriber
import net.lospi.dailyc.core.ImageFile
import net.lospi.dailyc.core.MessageBody
import net.lospi.mail.MailManager
import net.lospi.mail.core.EmailSendRequest

@Slf4j
class MailSender {
    MailManager mailManager
    String from

    def send(EmailSubscriber emailSubscriber, MessageBody messageBody, ImageFile imageFile) {
        log.info("[ ] Sending image {} to email {}; message: {}", imageFile, emailSubscriber, messageBody)
        def request = new EmailSendRequest(emailSubscriber.address, from, messageBody.contents, imageFile.image)
        def result = mailManager.send(request)
        if(result.successful)
            log.info("[+] Success! Response from mail server: {}", result.messageResponse)
        else
            log.error("[-] Failure! Response from mail server: %n{}", result.messageResponse)
        return result
    }
}
