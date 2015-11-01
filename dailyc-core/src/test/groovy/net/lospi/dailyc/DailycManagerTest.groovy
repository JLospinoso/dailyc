package net.lospi.dailyc

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
import net.lospi.mogreet.core.BaseResponse
import net.lospi.mogreet.core.SendResponse
import net.lospi.mogreet.core.UploadResponse
import spock.lang.Specification

class DailycManagerTest extends Specification {
    def dao = Mock(Dao)
    def sender = Mock(MogreetSender)
    def mailSender = Mock(MailSender)
    def subscriber = Mock(MogreetSubscriber)
    def mogreetUploader = Mock(MogreetUploader)
    def uploadResponse = Mock(UploadResponse)
    def subscribeResponse0 = Mock(BaseResponse)
    def subscribeResponse1 = Mock(BaseResponse)
    def sendResponse0 = Mock(SendResponse)
    def sendResponse1 = Mock(SendResponse)
    def emailSendResponse0 = Mock(SendResponse)
    def emailSendResponse1 = Mock(SendResponse)
    def imageFile = Mock(ImageFile)
    def subscribers = [Mock(Subscriber), Mock(Subscriber)]
    def emailSubscribers = [Mock(EmailSubscriber), Mock(EmailSubscriber)]
    def messageBody = Mock(MessageBody)
    def batchLoader = Mock(BatchLoader)
    def contentId = "abc123"

    def "loads messages MMS subscribers email subscribers and images on batch"() {
        setup:
        def subscriberString = ["a", "b"]
        def messagesString = ["c", "d"]
        def emailSubscriberString = ["c", "d"]
        def jpegs = [Mock(File), Mock(File)]
        batchLoader.subscribers >> subscriberString
        batchLoader.messages >> messagesString
        batchLoader.jpegs >> jpegs
        batchLoader.emailSubscribers >> emailSubscriberString
        dao.loadImageFiles(jpegs) >> [Mock(ImageFile), Mock(ImageFile)]
        dao.loadMessages(messagesString) >> [Mock(MessageBody), Mock(MessageBody)]
        dao.loadSubscribers(subscriberString) >> [Mock(Subscriber), Mock(Subscriber)]
        dao.loadEmailSubscribers(emailSubscriberString) >> [Mock(EmailSubscriber), Mock(EmailSubscriber)]
        dao.nextImageFile() >> Mock(ImageFile)
        dao.nextMessageBody() >> Mock(MessageBody)
        def manager = new DailycManager(mogreetSender: sender, mogreetSubscriber: subscriber,
                mogreetUploader: mogreetUploader, dao: dao, batchLoader: batchLoader, mailSender: mailSender)

        when:
        manager.batch()

        then:
        true
    }

    def "runs when mogreet and email successful"() {
        setup:
        setupGenerators()
        setupUploader()
        setupSubscriber()
        setupSender()
        setupMailSender()
        def batch = new DailycManager(mogreetSender: sender, mogreetSubscriber: subscriber,
                mogreetUploader: mogreetUploader, dao: dao, mailSender: mailSender)

        when:
        batch.run()

        then:
        true
    }

    def "still runs when one send fails"() {
        setup:
        setupGenerators()
        setupUploader()
        setupSubscriber()
        setupMailSender()
        def successfulResponse = Mock(SendResponse)
        def unsuccessfulResponse = Mock(SendResponse)
        sender.send(subscribers[0], messageBody, contentId) >> successfulResponse
        sender.send(subscribers[1], messageBody, contentId) >> unsuccessfulResponse
        unsuccessfulResponse.isSuccessful() >> false
        successfulResponse.isSuccessful() >> true

        def batch = new DailycManager(mogreetSender: sender, mogreetSubscriber: subscriber,
                mogreetUploader: mogreetUploader, dao: dao, mailSender: mailSender)

        when:
        batch.run()

        then:
        def exception = thrown(RuntimeException)
        exception.message.contains("1 messages failed to send")
    }

    def "dies when uploader unsuccessful"() {
        setup:
        setupGenerators()
        mogreetUploader.upload(imageFile) >> uploadResponse
        uploadResponse.isSuccessful() >> false

        def batch = new DailycManager(mogreetSender: sender, mogreetSubscriber: subscriber,
                mogreetUploader: mogreetUploader, dao: dao, mailSender: mailSender)

        when:
        batch.run()

        then:
        def exception = thrown(RuntimeException)
        exception.message.contains("could not be uploaded")
    }

    def "dies when uploader throws"() {
        setup:
        setupGenerators()
        mogreetUploader.upload(imageFile) >> { throw new Exception() }

        def batch = new DailycManager(mogreetSender: sender, mogreetSubscriber: subscriber,
                mogreetUploader: mogreetUploader, dao: dao, mailSender: mailSender)

        when:
        batch.run()

        then:
        def exception = thrown(RuntimeException)
        exception.message.contains("could not be uploaded")
    }

    def setupGenerators() {
        dao.getEmailSubscribers() >> emailSubscribers
        dao.getSubscribers() >> subscribers
        dao.nextImageFile() >> imageFile
        dao.nextMessageBody() >> messageBody
    }

    def setupUploader() {
        mogreetUploader.upload(imageFile) >> uploadResponse
        uploadResponse.isSuccessful() >> true
        uploadResponse.contentIdResponse >> contentId
    }

    def setupSubscriber() {
        subscriber.subscribe(subscribers[0]) >> subscribeResponse0
        subscriber.subscribe(subscribers[1]) >> subscribeResponse1
        subscribeResponse0.isSuccessful() >> true
        subscribeResponse1.isSuccessful() >> true
    }

    def setupSender() {
        sender.send(subscribers[0], messageBody, contentId) >> sendResponse0
        sender.send(subscribers[1], messageBody, contentId) >> sendResponse1
        sendResponse0.isSuccessful() >> true
        sendResponse1.isSuccessful() >> true
    }

    def setupMailSender() {
        mailSender.send(emailSubscribers[0], messageBody, imageFile) >> emailSendResponse0
        mailSender.send(emailSubscribers[1], messageBody, imageFile) >> emailSendResponse1
        emailSendResponse0.isSuccessful() >> true
        emailSendResponse1.isSuccessful() >> true
    }
}
