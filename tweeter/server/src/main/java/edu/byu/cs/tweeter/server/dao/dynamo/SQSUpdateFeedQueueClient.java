package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.net.request.UpdateFeedRequest;
import edu.byu.cs.tweeter.server.dao.UpdateFeedQueueClient;

public class SQSUpdateFeedQueueClient implements UpdateFeedQueueClient {
    @Override
    public boolean postStatusAndFollowersToQueue(UpdateFeedRequest updateFeedRequest) {
        String serializedUpdateFeedRequest = (new Gson()).toJson(updateFeedRequest);
        String queueUrl = "https://sqs.us-east-2.amazonaws.com/318916037125/UpdateFeedQueue";

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(serializedUpdateFeedRequest);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        String msgId = send_msg_result.getMessageId();
        System.out.println(msgId);
        return msgId != null;
    }
}
