package net.lospi.dailyc.util

import spock.lang.Specification

import java.security.MessageDigest

class HasherTest extends Specification {
    def "Hash"() {
        setup:
        byte[] fileBytes = [1, 2, 3]
        byte[] expectedResult = [3, 4, 5]
        def digest = Mock(MessageDigest)
        digest.digest({ b -> fileBytes }) >> expectedResult
        def hasher = new Hasher(digest: digest)

        when:
        def result = hasher.hash(fileBytes)

        then:
        assert result.bytes == expectedResult
        1 * digest.reset()
    }
}
