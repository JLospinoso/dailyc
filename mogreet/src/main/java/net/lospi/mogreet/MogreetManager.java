package net.lospi.mogreet;

import net.lospi.exception.DevelopmentException;
import net.lospi.exception.MogreetException;
import net.lospi.mogreet.core.*;
import net.lospi.mogreet.http.GetRequestEncoder;
import net.lospi.mogreet.http.RequestManager;
import net.lospi.mogreet.http.PostRequestEncoder;
import net.lospi.mogreet.parse.Parser;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static net.lospi.mogreet.util.MogreetUrl.*;

public class MogreetManager {
    private static final String GET_REQUEST_IMPROPERLY_ENCODED_S = "GET Request improperly encoded: %s";
    private final RequestManager requestManager;
    private final Parser<SendResponse> sendResultParser;
    private final Parser<UploadResponse> uploadResultParser;
    private final Parser<BaseResponse> baseResponseParser;
    private final GetRequestEncoder getRequestEncoder;
    private final PostRequestEncoder postRequestEncoder;

    public MogreetManager(RequestManager requestManager,
                          Parser<SendResponse> sendResultParser,
                          Parser<UploadResponse> uploadResponseParser,
                          Parser<BaseResponse> baseResponseParser,
                          GetRequestEncoder getRequestEncoder,
                          PostRequestEncoder postRequestEncoder) {
        this.requestManager = requestManager;
        this.sendResultParser = sendResultParser;
        this.uploadResultParser = uploadResponseParser;
        this.baseResponseParser = baseResponseParser;
        this.getRequestEncoder = getRequestEncoder;
        this.postRequestEncoder = postRequestEncoder;
    }

    public BaseResponse ping() throws MogreetException {
        HttpGet getRequest = getRequestEncoder.with()
                .baseUrl(PING_URL)
                .build();
        return requestManager.executeRequest(getRequest, baseResponseParser);
    }

    public SendResponse send(SendRequest sendRequest) throws MogreetException {
        HttpGet getRequest = null;
        try {
            getRequest = getRequestEncoder.with()
                    .baseUrl(SEND_URL)
                    .parameter("content_id", sendRequest.getContentId())
                    .parameter("campaign_id", sendRequest.getCampaignId())
                    .parameter("message", sendRequest.getMessage())
                    .parameter("from", sendRequest.getFrom())
                    .parameter("to", sendRequest.getTo())
                    .build();
        } catch (UnsupportedEncodingException e) {
            throw new DevelopmentException(String.format(GET_REQUEST_IMPROPERLY_ENCODED_S, e));
        }
        return requestManager.executeRequest(getRequest, sendResultParser);
    }

    public UploadResponse upload(UploadImageRequest uploadImageRequest) throws MogreetException {
        HttpPost postRequest = null;
        try {
            postRequest = postRequestEncoder.with()
                    .baseUrl(UPLOAD_URL)
                    .parameter("type", "image")
                    .parameter("name", uploadImageRequest.getName())
                    .image(uploadImageRequest.getImage())
                    .imageFileName(uploadImageRequest.getName())
                    .build();
        } catch (IOException e) {
            throw new DevelopmentException(String.format(GET_REQUEST_IMPROPERLY_ENCODED_S, e));
        }
        return requestManager.executeRequest(postRequest, uploadResultParser);
    }

    public BaseResponse subscribe(SubscribeRequest subscribeRequest) throws MogreetException {
        HttpGet getRequest = null;
        try {
            getRequest = getRequestEncoder.with()
                    .baseUrl(SETOPT_URL)
                    .parameter("number", subscribeRequest.getNumber())
                    .parameter("campaign_id", subscribeRequest.getCampaignId())
                    .parameter("status_code", subscribeRequest.getStatusCode())
                    .build();
        } catch (UnsupportedEncodingException e) {
            throw new DevelopmentException(String.format(GET_REQUEST_IMPROPERLY_ENCODED_S, e));
        }
        return requestManager.executeRequest(getRequest, baseResponseParser);
    }
}