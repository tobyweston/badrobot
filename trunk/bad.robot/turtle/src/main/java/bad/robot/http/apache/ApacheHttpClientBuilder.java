package bad.robot.http.apache;

import bad.robot.turtle.Defect;
import com.google.code.tempusfugit.temporal.Duration;
import org.apache.http.HttpHost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import static javax.net.ssl.SSLContext.getInstance;
import static org.apache.http.client.params.ClientPNames.*;
import static org.apache.http.conn.params.ConnRoutePNames.DEFAULT_PROXY;
import static org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;
import static org.apache.http.params.CoreProtocolPNames.USE_EXPECT_CONTINUE;

public class ApacheHttpClientBuilder {

    private Duration timeout = Duration.seconds(30);
    private List<ApacheHttpAuthenticationCredentials> credentials = new ArrayList<ApacheHttpAuthenticationCredentials>();
    private HttpHost proxy;

    public ApacheHttpClientBuilder with(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public ApacheHttpClientBuilder withProxy(HttpHost proxy) {
        this.proxy = proxy;
        return this;
    }

    public ApacheHttpClientBuilder with(ApacheHttpAuthenticationCredentials login) {
        credentials.add(login);
        return this;
    }

    public org.apache.http.client.HttpClient build() {
        HttpParams httpParameters = createAndConfigureHttpParameters();
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(httpParameters, createSchemeRegistry());
        DefaultHttpClient client = new DefaultHttpClient(connectionManager, httpParameters);
        setupAuthorisation(client);
        return client;
    }

    private SchemeRegistry createSchemeRegistry() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", createSslSocketFactory(), 443));
        return registry;
    }

    private SSLSocketFactory createSslSocketFactory() {
        try {
            SSLContext ctx = getInstance("TLS");
            ctx.init(null, new TrustManager[]{new TolerantCertificateManager()}, null);
            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(ctx);
            sslSocketFactory.setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
            return sslSocketFactory;
        } catch (NoSuchAlgorithmException e) {
            throw new Defect(e);
        } catch (KeyManagementException e) {
            throw new Defect(e);
        }
    }

    private HttpParams createAndConfigureHttpParameters() {
        HttpParams parameters = createHttpParametersViaNastyHackButBetterThanCopyAndPaste();
        parameters.setParameter(CONNECTION_TIMEOUT, (int) timeout.inMillis());
        parameters.setParameter(SO_TIMEOUT, (int) timeout.inMillis());
        parameters.setParameter(HANDLE_REDIRECTS, true);
        parameters.setParameter(ALLOW_CIRCULAR_REDIRECTS, true);
        parameters.setParameter(HANDLE_AUTHENTICATION, true);
        parameters.setParameter(USE_EXPECT_CONTINUE, true);
        parameters.setParameter(DEFAULT_PROXY, proxy);
        HttpClientParams.setRedirecting(parameters, true);
        return parameters;
    }

    protected HttpParams createHttpParametersViaNastyHackButBetterThanCopyAndPaste() {
        return new DefaultHttpClient() {
            @Override
            protected HttpParams createHttpParams() {
                return super.createHttpParams();
            }
        }.createHttpParams();
    }

    private void setupAuthorisation(DefaultHttpClient client) {
        for (ApacheHttpAuthenticationCredentials credentials : this.credentials)
            client.getCredentialsProvider().setCredentials(credentials.getScope(), credentials.getUser());
    }

     private static class TolerantCertificateManager implements TrustManager {

        public void checkClientTrusted(X509Certificate[] certificates, String string) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] certificates, String string) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
