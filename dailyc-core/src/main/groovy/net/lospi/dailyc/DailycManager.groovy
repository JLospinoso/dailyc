package net.lospi.dailyc

import groovy.util.logging.Slf4j
import net.lospi.dailyc.comm.MogreetSender
import net.lospi.dailyc.comm.MogreetSubscriber
import net.lospi.dailyc.comm.MogreetUploader
import net.lospi.dailyc.core.ImageFile
import net.lospi.dailyc.core.MessageBody
import net.lospi.dailyc.core.Subscriber
import net.lospi.dailyc.persistence.BatchLoader
import net.lospi.dailyc.persistence.Dao
import net.lospi.mogreet.core.UploadResponse

@Slf4j
class DailycManager implements Runnable {
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
        log.debug("[ ] Updating subscribers")
        def subscribers = batchLoader.subscribers
        log.info("[+] Loaded {} subscribers from disk", subscribers.size())
        def nSubscribers = dao.loadSubscribers(subscribers).size()
        log.info("[+] {} subscribers updated", nSubscribers)
        def nextMessage = dao.nextMessageBody()
        def nextImage = dao.nextImageFile()
        log.info("[ ] Next message: {}", nextMessage.contents)
        log.info("[ ] Next image: {}", nextImage.name)
        if(nextMessage.lastUse)
            log.warn("[-] Next message has been used previously: {}", nextMessage.lastUse)
        if(nextImage.lastUse)
            log.warn("[-] Previous message has been used previously: {}", nextImage.lastUse)
    }

    void run() {
        def selectedFile = dao.nextImageFile()
        def selectedMessage = dao.nextMessageBody()
        def subscribers = dao.getSubscribers()

        validateInput(selectedMessage, selectedFile, subscribers)
        String contentId = uploadContent(selectedFile)
        int messagesSuccessful = sendMessages(subscribers, selectedMessage, selectedFile, contentId)
        reportOnBatchSuccess(messagesSuccessful, subscribers)
    }

    private void reportOnBatchSuccess(int messagesSuccessful, List<Subscriber> numbers) {
        if (messagesSuccessful == numbers.size()) {
            log.info("[+] Batch completed with all messages sent successfully.")
        } else {
            def numberFailed = numbers.size() - messagesSuccessful
            log.error("[-] Batch completed with {} of {} messages sent successfully.", messagesSuccessful, numbers.size())
            throw new RuntimeException(String.format("%d messages failed to send", numberFailed))
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

    private void validateInput(selectedMessage, selectedFile, numbers) {
        if (!selectedMessage)
            throw new RuntimeException("MessageBodyGenerator returned null message.")
        if (!selectedFile)
            throw new RuntimeException("ImageFileGenerator returned null ImageFile.")
        if (numbers.size() == 0)
            throw new RuntimeException("SubscriberGenerator returned no numbers.")
        log.info("[+] Selected message: \"{}\"", selectedMessage)
        log.info("[+] Selected file: {}", selectedFile)
        log.info("[+] {} subscribers.", numbers.size())
    }

    private int sendMessages(List<Subscriber> subscribers, MessageBody messageBody, ImageFile imageFile,
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
                    log.error("[-] Not sent to {}:\n{}\n{}", subscriber, subscribeResponse, sendResponse)
                }
                return sendResponse.successful
            } catch (any) {
                log.error("[-] Could not subscribe/send to {}:\n", subscriber, any.toString())
                return false
            }
        }
    }
}
