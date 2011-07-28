package bad.robot.http;

public interface HttpResponse {

    int getStatusCode();

    String getStatusMessage();

    String getContent();
}
