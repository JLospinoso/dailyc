package net.lospi.dailyc.core

import spock.lang.Specification

class ImageFileTest extends Specification {
    def date = Mock(Date)

    def "can toString"() {
        setup:
        def imageFile = new ImageFile(name: "name", hash: "hash", image: [1, 2, 3], lastUse: date)

        when:
        def result = imageFile.toString()

        then:
        result != null
    }
}
