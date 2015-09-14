package net.lospi.dailyc.core

import grails.persistence.Entity

@Entity
class Subscriber {
    String number
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
Subscriber{
    id=$id,
    number='$number',
    lastUse=$lastUse,
    version=$version
}"""
    }
}
