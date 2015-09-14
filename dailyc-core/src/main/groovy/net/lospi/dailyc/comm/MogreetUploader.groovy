package net.lospi.dailyc.comm

import groovy.util.logging.Slf4j
import net.lospi.dailyc.core.ImageFile
import net.lospi.mogreet.MogreetManager
import net.lospi.mogreet.core.UploadImageRequest
import net.lospi.mogreet.core.UploadResponse

@Slf4j
class MogreetUploader {
    MogreetManager manager

    def upload(ImageFile imageFile) {
        log.info("[ ] Uploading {}", imageFile)
        def request = new UploadImageRequest(imageFile.name, imageFile.image)
        def result = manager.upload(request)
        if(result.successful)
            log.info("[+] Success! Response from Mogreet: {}", result.messageResponse)
        else
            log.error("[-] Failure! Response from Mogreet: %n{}", result.xmlResponse)
        return result
    }
}
