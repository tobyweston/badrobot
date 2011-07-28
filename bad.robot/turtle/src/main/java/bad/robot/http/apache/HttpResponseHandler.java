package bad.robot.http.apache;

import bad.robot.http.DefaultHttpResponse;
import bad.robot.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

class HttpResponseHandler implements ResponseHandler<HttpResponse> {

    @Override
    public HttpResponse handleResponse(org.apache.http.HttpResponse response) throws IOException {
        return new DefaultHttpResponse(getStatusCodeFrom(response), getStatusMessageFrom(response), getContentFrom(response));
    }

    private static String getStatusMessageFrom(org.apache.http.HttpResponse response) {
        return response.getStatusLine().getReasonPhrase();
    }

    private static int getStatusCodeFrom(org.apache.http.HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private static String getContentFrom(org.apache.http.HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity == null)
            return "";
        try {
            return EntityUtils.toString(entity);
        } finally {
            cleanup(entity);
        }
    }

    private static void cleanup(HttpEntity response) throws IOException {
        if (response != null)
            response.consumeContent();
    }

}
