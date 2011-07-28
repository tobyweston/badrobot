package bad.robot.http;

import java.net.URL;

public interface HttpClient {

    HttpResponse get(URL url) throws HttpException;

    void shutdown();
}
