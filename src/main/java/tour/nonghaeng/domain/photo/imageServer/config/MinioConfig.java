package tour.nonghaeng.domain.photo.imageServer.config;

import io.minio.MinioClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@Configuration
@Getter
public class MinioConfig {

    @Value("${spring.cloud.minio.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.minio.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.minio.region.static}")
    private String region;

    @Value("${spring.cloud.minio.url}")
    private String url;


    @Bean
    public MinioClient minioClient(AwsCredentials awsCredentials) {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .region(region)
                .build();
    }



}
