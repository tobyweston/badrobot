package bad.robot.http.apache;

import bad.robot.http.HttpClient;
import bad.robot.http.HttpException;
import bad.robot.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.net.URL;
import java.util.concurrent.Callable;

import static com.google.code.tempusfugit.ExceptionWrapper.wrapAnyException;
import static com.google.code.tempusfugit.WithException.with;

public class ApacheHttpClient implements HttpClient {

    private final org.apache.http.client.HttpClient client;

    public ApacheHttpClient(ApacheHttpClientBuilder builder) {
        client = builder.build();
    }

    @Override
    public HttpResponse get(URL url) throws HttpException {
        HttpGet get = new HttpGet(url.toExternalForm());
        return wrapAnyException(execute(get), with(HttpException.class));
    }

    private Callable<HttpResponse> execute(final HttpUriRequest request) {
        return new Callable<HttpResponse>() {
            @Override
            public HttpResponse call() throws Exception {
                return client.execute(request, new HttpResponseHandler());
            }
        };
    }

    @Override
    public void shutdown() {
        client.getConnectionManager().shutdown();
    }

}
