package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;

import edu.byu.cs.tweeter.server.dao.ImageDAO;

public class S3DAO implements ImageDAO {
    private static final String BUCKET_NAME = "tweeterprofilepictures";

    @Override
    public String upload(String alias, String imageBytesBase64) {
        URL url;
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    .withRegion("us-east-2")
                    .build();

            String fileName = String.format("%s_profile_image", alias);

            byte[] imageBytes = Base64.getDecoder().decode(imageBytesBase64);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageBytes.length);
            metadata.setContentType("image/jpeg");


            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, fileName, new ByteArrayInputStream(imageBytes), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);


            s3.putObject(putObjectRequest);

            url = s3.getUrl(BUCKET_NAME, fileName);

        } catch (Exception ex) {
            throw new RuntimeException("[InternalServerError] Could upload profile picture to S3");
        }
        return url.toString();
    }
}
