package senla.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import senla.service.MinioService;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public String upload(MultipartFile file) {

        try {
            String object = UUID.randomUUID() + "_" + file.getOriginalFilename();

            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .method(Method.GET)
                    .build());

        } catch (Exception e) {
            log.error("Ошибка при загрузке файла в Minio", e);
            throw new ServiceException(ServiceExceptionEnum.MINIO_ERROR, e.getMessage());
        }
    }

    public void delete(String url) {
        try {
            String path = UriComponentsBuilder.fromUriString(url).build().getPath();
            String object = path.substring(bucket.length() + 2);
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .build());
        } catch (Exception e) {
            log.error("Ошибка при удалении файла из Minio", e);
            throw new ServiceException(ServiceExceptionEnum.MINIO_ERROR, e.getMessage());
        }
    }
}
