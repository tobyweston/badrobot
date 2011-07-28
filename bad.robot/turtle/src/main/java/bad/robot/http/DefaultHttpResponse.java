package bad.robot.http;

public class DefaultHttpResponse implements HttpResponse {
    private final int statusCode;
    private final String statusMessage;
    private final String content;

    public DefaultHttpResponse(int statusCode, String statusMessage, String content) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.content = content;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "DefaultHttpResponse{statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
