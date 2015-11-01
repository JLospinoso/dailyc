package net.lospi.mail.multipart;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

public class JpegDataSourceFactory {
    public DataSource create(byte[] image) {
        return new ByteArrayDataSource(image, "application/jpg");
    }
}
