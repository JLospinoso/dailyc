package net.lospi.mail.core;

import java.util.Arrays;

public class EmailSendRequest {
    private final String to;
    private final String from;
    private final String contents;
    private final byte[] image;

    public EmailSendRequest(String to, String from, String contents, byte[] image) {
        this.to = to;
        this.from = from;
        this.contents = contents;
        this.image = image;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getContents() {
        return contents;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmailSendRequest{");
        sb.append("to='").append(to).append('\'');
        sb.append(", from='").append(from).append('\'');
        sb.append(", contents='").append(contents).append('\'');
        sb.append(", image=").append(Arrays.toString(image));
        sb.append('}');
        return sb.toString();
    }
}
