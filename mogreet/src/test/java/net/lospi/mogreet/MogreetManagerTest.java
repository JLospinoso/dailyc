package net.lospi.mogreet;

import net.lospi.mogreet.core.*;
import net.lospi.mogreet.http.GetRequestEncoder;
import net.lospi.mogreet.http.PostRequestEncoder;
import net.lospi.mogreet.http.RequestManager;
import net.lospi.mogreet.parse.Parser;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static net.lospi.mogreet.util.MogreetUrl.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@Test
@SuppressWarnings("unchecked")
public class MogreetManagerTest {
    private SubscribeRequest subscribeRequest;
    private SendRequest sendRequest;
    private PostRequestEncoder postRequestEncoder;
    private Parser<BaseResponse> baseResponseParser;
    private Parser<SendResponse> sendResultParser;
    private Parser<UploadResponse> uploadResponseParser;
    private GetRequestEncoder getRequestEncoder;
    private UploadImageRequest uploadImageRequest;
    private RequestManager requestManager;
    private SendResponse sendResponse;
    private UploadResponse uploadResponse;
    private BaseResponse baseResponse;
    private GetRequestEncoder.Stub getRequestEncoderStub;
    private PostRequestEncoder.Stub postRequestEncoderStub;
    private HttpGet getRequest;
    private HttpPost postRequest;
    private byte[] imageBytes;
    private String imageName;

    @BeforeMethod
    public void setUp() throws IOException, URISyntaxException {
        imageName = "image_name.jpg";
        imageBytes = new byte[]{1, 2, 3};
        subscribeRequest = new SubscribeRequest("1", "2", "3");
        sendRequest = new SendRequest("4", "5", "6", "7", "8");
        uploadImageRequest = new UploadImageRequest(imageName, imageBytes);

        getRequest = mock(HttpGet.class);
        postRequest = mock(HttpPost.class);
        getRequestEncoderStub = mock(GetRequestEncoder.Stub.class);
        postRequestEncoderStub = mock(PostRequestEncoder.Stub.class);
        sendResponse = mock(SendResponse.class);
        uploadResponse = mock(UploadResponse.class);
        baseResponse = mock(BaseResponse.class);
        baseResponseParser = mock(Parser.class);
        requestManager = mock(RequestManager.class);
        postRequestEncoder = mock(PostRequestEncoder.class);
        sendResultParser = mock(Parser.class);
        uploadResponseParser = mock(Parser.class);
        getRequestEncoder = mock(GetRequestEncoder.class);


        when(getRequestEncoder.with()).thenReturn(getRequestEncoderStub);
        when(postRequestEncoder.with()).thenReturn(postRequestEncoderStub);

        when(getRequestEncoderStub.baseUrl(any(String.class))).thenReturn(getRequestEncoderStub);
        when(getRequestEncoderStub.parameter(any(String.class), any(String.class))).thenReturn(getRequestEncoderStub);
        when(getRequestEncoderStub.build()).thenReturn(getRequest);

        when(postRequestEncoderStub.baseUrl(any(String.class))).thenReturn(postRequestEncoderStub);
        when(postRequestEncoderStub.parameter(any(String.class), any(String.class))).thenReturn(postRequestEncoderStub);
        when(postRequestEncoderStub.image(imageBytes)).thenReturn(postRequestEncoderStub);
        when(postRequestEncoderStub.imageFileName(imageName)).thenReturn(postRequestEncoderStub);
        when(postRequestEncoderStub.build()).thenReturn(postRequest);
    }

    public void ping() throws Exception {
        MogreetManager underStudy = new MogreetManager(requestManager, sendResultParser, uploadResponseParser, baseResponseParser, getRequestEncoder, postRequestEncoder);
        when(requestManager.executeRequest(getRequest, baseResponseParser)).thenReturn(baseResponse);

        BaseResponse result = underStudy.ping();

        assertThat(result, is(baseResponse));
        verify(getRequestEncoderStub).baseUrl(PING_URL);
        verify(getRequestEncoderStub).build();
        verifyNoMoreInteractions(getRequestEncoderStub);
    }

    public void send() throws Exception {
        MogreetManager underStudy = new MogreetManager(requestManager, sendResultParser, uploadResponseParser, baseResponseParser, getRequestEncoder, postRequestEncoder);
        when(requestManager.executeRequest(getRequest, sendResultParser)).thenReturn(sendResponse);

        SendResponse result = underStudy.send(sendRequest);

        assertThat(result, is(sendResponse));
        verify(getRequestEncoderStub).baseUrl(SEND_URL);
        verify(getRequestEncoderStub).parameter("content_id", sendRequest.getContentId());
        verify(getRequestEncoderStub).parameter("campaign_id", sendRequest.getCampaignId());
        verify(getRequestEncoderStub).parameter("message", sendRequest.getMessage());
        verify(getRequestEncoderStub).parameter("from", sendRequest.getFrom());
        verify(getRequestEncoderStub).parameter("to", sendRequest.getTo());
        verify(getRequestEncoderStub).build();
        verifyNoMoreInteractions(getRequestEncoderStub);
    }

    public void subscribe() throws Exception {
        MogreetManager underStudy = new MogreetManager(requestManager, sendResultParser, uploadResponseParser, baseResponseParser, getRequestEncoder, postRequestEncoder);
        when(requestManager.executeRequest(getRequest, baseResponseParser)).thenReturn(baseResponse);

        BaseResponse result = underStudy.subscribe(subscribeRequest);

        assertThat(result, is(baseResponse));
        verify(getRequestEncoderStub).baseUrl(SETOPT_URL);
        verify(getRequestEncoderStub).parameter("number", subscribeRequest.getNumber());
        verify(getRequestEncoderStub).parameter("status_code", subscribeRequest.getStatusCode());
        verify(getRequestEncoderStub).parameter("campaign_id", subscribeRequest.getCampaignId());
        verify(getRequestEncoderStub).build();
        verifyNoMoreInteractions(getRequestEncoderStub);
    }

    public void upload() throws Exception {
        MogreetManager underStudy = new MogreetManager(requestManager, sendResultParser, uploadResponseParser, baseResponseParser, getRequestEncoder, postRequestEncoder);
        when(requestManager.executeRequest(postRequest, uploadResponseParser)).thenReturn(uploadResponse);

        UploadResponse result = underStudy.upload(uploadImageRequest);
        assertThat(result, is(uploadResponse));

        verify(postRequestEncoderStub).baseUrl(UPLOAD_URL);
        verify(postRequestEncoderStub).parameter("type", "image");
        verify(postRequestEncoderStub).parameter("name", uploadImageRequest.getName());
        verify(postRequestEncoderStub).image(imageBytes);
        verify(postRequestEncoderStub).imageFileName(imageName);
        verify(postRequestEncoderStub).build();
        verifyNoMoreInteractions(getRequestEncoderStub);
    }
}
