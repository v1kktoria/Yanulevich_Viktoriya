package senla.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    String upload(MultipartFile file);

    void delete(String url);
}
