package net.lospi.dailyc.core

import grails.persistence.Entity

@Entity
class SentMms {
    Subscriber subscriber
    MessageBody messageBody
    ImageFile imageFile
    Date sentDate

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SentMms{");
        sb.append("subscriber=").append(subscriber);
        sb.append(", messageBody=").append(messageBody);
        sb.append(", imageFile=").append(imageFile);
        sb.append(", sentDate=").append(sentDate);
        sb.append('}');
        return sb.toString();
    }
}
