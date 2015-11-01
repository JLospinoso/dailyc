package net.lospi.mail.multipart;

import org.apache.commons.mail.MultiPartEmail;

public class MultipartMailFactory {
    public MultiPartEmail create() {
        return new MultiPartEmail();
    }
}
