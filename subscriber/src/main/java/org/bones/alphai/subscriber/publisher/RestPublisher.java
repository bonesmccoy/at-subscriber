package org.bones.alphai.subscriber.publisher;

import org.bones.alphai.subscriber.stream.UpdateInterface;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;



public class RestPublisher implements PublisherInterface{

    private String collectorUrl;

    public RestPublisher(String collectorUrl)
    {
        this.collectorUrl = collectorUrl;
    }

    public void publish(UpdateInterface update)
    {
        HttpClient client = HttpClientBuilder.create().build();

        try {

            HttpPost request = new HttpPost(collectorUrl);
            StringEntity requestEntity = new StringEntity(
                    update.toJsonString(),
                    ContentType.APPLICATION_JSON
            );

            request.setEntity(requestEntity);
            HttpResponse rawResponse = client.execute(request);

        } catch (Exception ex) {
           System.out.println(ex.toString());
        }
    }
}
