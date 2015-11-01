package net.lospi.dailyc.core

import grails.persistence.Entity

@Entity
class EmailSubscriber {
    String address
    Date lastUse

    static hasMany = [
        sentEmail : SentEmail
    ]

    static constraints = {
        lastUse(nullable: true)
    }

    @Override
    public String toString() {
        return """\
EmailSubscriber{
    id=$id,
    address='$address',
    lastUse=$lastUse,
    version=$version
}"""
    }
}
