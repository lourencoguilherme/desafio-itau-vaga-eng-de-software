package desafio.itau.infrastructure.bucket.repository.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.itau.infrastructure.bucket.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class S3Repository<T> implements BucketRepository<T> {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void saveInFile(String dirOrBucketName, String key, T object) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(object);

        // Salvar o JSON no Amazon S3
        amazonS3.putObject(dirOrBucketName, key, "");
    }

}
