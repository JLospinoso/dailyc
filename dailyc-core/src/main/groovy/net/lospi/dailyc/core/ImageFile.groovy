package net.lospi.dailyc.core

import grails.persistence.Entity

@Entity
class ImageFile {
    String name
    String hash
    byte[] image
    Date lastUse

    static hasMany = [
        sentMms : SentMms
    ]

    static constraints = {
        image(maxSize: 10*1024*1024)
        lastUse(nullable: true)
    }


    @Override
    public String toString() {
        return """\
ImageFile{
    id=$id,
    name='$name',
    hash='$hash',
    imageSize=${image.size()},
    lastUse=$lastUse,
    version=$version
}"""
    }
}
