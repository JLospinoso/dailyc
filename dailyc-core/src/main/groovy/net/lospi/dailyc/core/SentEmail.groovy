package net.lospi.dailyc.core

import grails.persistence.Entity

@Entity
class SentEmail {
    EmailSubscriber emailSubscriber
    MessageBody messageBody
    ImageFile imageFile
    Date sentDate

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SentEmail{");
        sb.append("emailSubscriber=").append(emailSubscriber);
        sb.append(", messageBody=").append(messageBody);
        sb.append(", imageFile=").append(imageFile);
        sb.append(", sentDate=").append(sentDate);
        sb.append('}');
        return sb.toString();
    }
}
