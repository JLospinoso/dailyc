package net.lospi.dailyc.util

import java.security.MessageDigest

class Hasher {
    MessageDigest digest

    String hash(byte[] bytes){
        digest.reset()
        def candidateHash = digest.digest(bytes)
        return new String(candidateHash)
    }
}
