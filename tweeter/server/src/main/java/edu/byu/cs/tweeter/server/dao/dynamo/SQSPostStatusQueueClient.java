package edu.byu.cs.tweeter.server.dao.dynamo;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.PostUpdateFeedRequest;
import edu.byu.cs.tweeter.server.dao.PostStatusQueueClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;

public class SQSPostStatusQueueClient implements PostStatusQueueClient {
    @Override
    public boolean postStatusToQueue(Status status, AuthToken authToken) {
        PostUpdateFeedRequest postUpdateFeedRequest = new PostUpdateFeedRequest(status, authToken);
        String serializedPostUpdateFeedRequest = (new Gson()).toJson(postUpdateFeedRequest);
        String queueUrl = "https://sqs.us-east-2.amazonaws.com/318916037125/SQSPostStatusQueue";

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(serializedPostUpdateFeedRequest);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        String msgId = send_msg_result.getMessageId();
        System.out.println(msgId);
        return msgId != null;
    }
}
