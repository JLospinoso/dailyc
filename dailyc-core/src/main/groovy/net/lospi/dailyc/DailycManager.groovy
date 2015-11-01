package net.lospi.dailyc

import groovy.util.logging.Slf4j
import net.lospi.dailyc.comm.MailSender
import net.lospi.dailyc.comm.MogreetSender
import net.lospi.dailyc.comm.MogreetSubscriber
import net.lospi.dailyc.comm.MogreetUploader
import net.lospi.dailyc.core.EmailSubscriber
import net.lospi.dailyc.core.ImageFile
import net.lospi.dailyc.core.MessageBody
import net.lospi.dailyc.core.Subscriber
import net.lospi.dailyc.persistence.BatchLoader
import net.lospi.dailyc.persistence.Dao
import net.lospi.mail.MailManager
import net.lospi.mogreet.core.UploadResponse

@Slf4j
class DailycManager implements Runnable {
    MailSender mailSender
    MogreetSender mogreetSender
    MogreetSubscriber mogreetSubscriber
    MogreetUploader mogreetUploader
    BatchLoader batchLoader
    Dao dao

    void batch() {
        log.debug("[ ] Updating images")
        def jpegs = batchLoader.jpegs
        log.info("[+] Loaded {} images from disk", jpegs.size())
        def nFiles = dao.loadImageFiles(jpegs).size()
        log.info("[+] {} images updated", nFiles)
        log.debug("[ ] Updating messages")
        def messages = batchLoader.messages
        log.info("[+] Loaded {} messages from disk", messages.size())
        def nMessages = dao.loadMessages(messages).size()
        log.info("[+] {} messages updated", nMessages)
        log.debug("[ ] Updating MMS subscribers")
        def subscribers = batchLoader.subscribers
        log.info("[+] Loaded {} MMS subscribers from disk", subscribers.size())
        def nSubscribers = dao.loadSubscribers(subscribers).size()
        log.info("[+] {} MMS subscribers updated", nSubscribers)
        log.debug("[ ] Updating email subscribers")
        def emailSubscribers = batchLoader.emailSubscribers
        log.info("[+] Loaded {} email subscribers from disk", emailSubscribers.size())
        def nEmailSubscribers = dao.loadEmailSubscribers(emailSubscribers).size()
        log.info("[+] {} email subscribers updated", nEmailSubscribers)
        def nextMessage = dao.nextMessageBody()
        def nextImage = dao.nextImageFile()
        log.info("[ ] Next message: {}", nextMessage.contents)
        log.info("[ ] Next image: {}", nextImage.name)
        if(nextMessage.lastUse)
            log.warn("[-] Next message has been used previously: {}", nextMessage.lastUse)
        if(nextImage.lastUse)
            log.warn("[-] Next image has been used previously: {}", nextImage.lastUse)
    }

    void run() {
        def selectedFile = dao.nextImageFile()
        def selectedMessage = dao.nextMessageBody()
        def subscribers = dao.getSubscribers()
        def emailSubscribers = dao.getEmailSubscribers()

        validateInput(selectedMessage, selectedFile, subscribers, emailSubscribers)
        String contentId = uploadContent(selectedFile)
        int mmsSuccessful = sendMms(subscribers, selectedMessage, selectedFile, contentId)
        int emailSuccessful = sendEmail(emailSubscribers, selectedMessage, selectedFile)
        reportOnBatchSuccess(mmsSuccessful, emailSuccessful, subscribers, emailSubscribers)
    }

    private void reportOnBatchSuccess(int mmsSuccessful, int emailSuccessful,
                                      List<Subscriber> numbers, List<EmailSubscriber> emails) {
        def numberRequested = numbers.size() + emails.size()
        def numberSuccessful = mmsSuccessful + emailSuccessful;
        if (numberRequested == numberSuccessful) {
            log.info("[+] Batch completed with all messages sent successfully.")
        } else {
            log.error("[-] Batch completed with {} of {} messages sent successfully.", numberSuccessful, numberRequested)
            throw new RuntimeException(String.format("%d messages failed to send", numberRequested - numberSuccessful))
        }
    }

    private String uploadContent(ImageFile selectedFile) {
        UploadResponse uploadResponse = null
        String contentId
        try {
            uploadResponse = mogreetUploader.upload(selectedFile)
            contentId = uploadResponse.contentIdResponse
        } catch (any) {
            throw new RuntimeException(
                    String.format("%s could not be uploaded:\n%s", selectedFile, uploadResponse),
                    any)
        }
        if (uploadResponse.successful)
            log.info("[+] {} uploaded with content id {}", selectedFile, contentId)
        else
            throw new RuntimeException(String.format("%s could not be uploaded:\n%s", selectedFile, uploadResponse))
        contentId
    }

    private void validateInput(selectedMessage, selectedFile, numbers, emails) {
        if (!selectedMessage)
            throw new RuntimeException("MessageBodyGenerator returned null message.")
        if (!selectedFile)
            throw new RuntimeException("ImageFileGenerator returned null ImageFile.")
        if (numbers.size() == 0)
            throw new RuntimeException("SubscriberGenerator returned no numbers.")
        if (emails.size() == 0)
            throw new RuntimeException("EmailSubscriberGenerator returned no numbers.")
        log.info("[+] Selected message: \"{}\"", selectedMessage)
        log.info("[+] Selected file: {}", selectedFile)
        log.info("[+] {} MMS subscribers.", numbers.size())
        log.info("[+] {} email subscribers.", numbers.size())
    }

    private int sendMms(List<Subscriber> subscribers, MessageBody messageBody, ImageFile imageFile,
                             String contentId) {
        return subscribers.count{ subscriber ->
            try {
                def subscribeResponse = mogreetSubscriber.subscribe(subscriber)
                def sendResponse = mogreetSender.send(subscriber, messageBody, contentId)
                if (sendResponse.successful) {
                    Date date = new Date()
                    def mms = dao.createSentMms(subscriber, messageBody, imageFile, date)
                    log.info("[+] Sent MMS \n{}", mms)
                } else {
                    log.error("[-] MMS not sent to {}:\n{}\n{}", subscriber, subscribeResponse, sendResponse)
                }
                return sendResponse.successful
            } catch (any) {
                log.error("[-] Could not subscribe/send to {}: {}\n", subscriber, any.toString())
                return false
            }
        }
    }

    private int sendEmail(List<EmailSubscriber> emailSubscribers, MessageBody messageBody, ImageFile imageFile) {
        return emailSubscribers.count{ subscriber ->
            try {
                def sendResponse = mailSender.send(subscriber, messageBody, imageFile)
                if (sendResponse.successful) {
                    Date date = new Date()
                    def email = dao.createSentEmail(subscriber, messageBody, imageFile, date)
                    log.info("[+] Sent email \n{}", email)
                } else {
                    log.error("[-] Email not sent to {}:\n{}", subscriber, sendResponse)
                }
                return sendResponse.successful
            } catch (any) {
                log.error("[-] Could not send to {}: {}\n", subscriber, any.toString())
                return false
            }
        }
    }
}
