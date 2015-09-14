package net.lospi.dailyc.persistence

import groovy.util.logging.Slf4j
import net.lospi.dailyc.core.*
import net.lospi.dailyc.util.Hasher

@Slf4j
class Dao {
    Hasher hasher

    List<Subscriber> loadSubscribers(List<String> subscribers) {
        subscribers.collect{Subscriber.findOrSaveByNumber(it)}
    }

    List<MessageBody> loadMessages(List<String> messages) {
        messages.collect{MessageBody.findOrSaveByContents(it)}
    }

    List<ImageFile> loadImageFiles(List<File> jpegs) {
        def imageFiles = jpegs.collect{
            def fileBytes = it.getBytes()
            def hash = hasher.hash(fileBytes)
            def imageFile = ImageFile.findOrCreateByHash(hash)
            imageFile.image = fileBytes
            imageFile.name = it.name
            imageFile.save()
        }
        imageFiles
    }

    MessageBody nextMessageBody() {
        def orderedMessages = MessageBody.findAll("from MessageBody as b order by b.lastUse asc")
        if (orderedMessages.size() == 0)
            throw new OutOfMessagesException()
        orderedMessages.first()
    }

    ImageFile nextImageFile() {
        def unsentImages = ImageFile.findAll("from ImageFile as i order by i.lastUse asc")
        if (unsentImages.size() == 0)
            throw new OutOfImagesException()
        unsentImages.first()
    }

    List<Subscriber> getSubscribers() {
        def subscribers = Subscriber.getAll()
        if(subscribers.size() == 0)
            throw new NoSubscribersException()
        subscribers
    }

    SentMms createSentMms(Subscriber subscriber, MessageBody messageBody, ImageFile imageFile, Date sentDate) {
        SentMms sentMms = new SentMms(subscriber: subscriber, messageBody: messageBody, imageFile: imageFile,
                sentDate: sentDate)
        subscriber.lastUse = sentDate
        messageBody.lastUse = sentDate
        imageFile.lastUse = sentDate
        subscriber.save()
        messageBody.save()
        imageFile.save()
        sentMms.save()
    }
}
