package net.lospi.dailyc.core

import grails.persistence.Entity


@Entity
class MessageBody {
    String contents
    Date lastUse

    static hasMany = [
        sentMms : SentMms
    ]

    static constraints = {
        lastUse(nullable: true)
    }

    @Override
    public String toString() {
        return """\
MessageBody{
    id=$id,
    contents='$contents',
    lastUse=$lastUse,
    version=$version
}"""
    }
}
