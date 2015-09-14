package net.lospi.dailyc.comm

import net.lospi.dailyc.core.ImageFile
import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.UploadResponse
import org.codehaus.groovy.runtime.ResourceGroovyMethods
import spock.lang.Specification

class MogreetUploaderTest extends Specification {
    def name = "foo.jpg"
    def imageFile = Mock(ImageFile)
    def manager = Mock(MogreetManager)
    def expectedResponse = Mock(UploadResponse)
    byte[] imageBytes = [1, 2, 3]

    def "can upload an image"() {
        setup:
        imageFile.name >> name
        imageFile.image >> imageBytes
        manager.upload({ r -> r.name == name &&
                r.image == imageBytes}) >> expectedResponse
        expectedResponse.successful >> true

        def uploader = new MogreetUploader(manager: manager)

        when:
        def result = uploader.upload(imageFile)

        then:
        assert result == expectedResponse
    }

    def "graceful when unsuccessful"() {
        setup:
        imageFile.name >> name
        imageFile.image >> imageBytes
        manager.upload({ r -> r.name == name &&
                r.image == imageBytes}) >> expectedResponse
        expectedResponse.successful >> false

        def uploader = new MogreetUploader(manager: manager)

        when:
        def result = uploader.upload(imageFile)

        then:
        assert result == expectedResponse
    }
}