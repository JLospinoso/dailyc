package net.lospi.mogreet.core;

public class UploadImageRequest {
    private final byte[] image;
    private final String name;

    public UploadImageRequest(String name, byte[] image) {
        this.image = image;
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadImageRequest{");
        sb.append("imageSize=").append(image.length);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
