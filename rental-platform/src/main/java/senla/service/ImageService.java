package senla.service;

import org.springframework.web.multipart.MultipartFile;
import senla.dto.ImageDto;

import java.util.List;

public interface ImageService {

    ImageDto create(Integer propertyId, MultipartFile file);

    ImageDto getById(Integer id);

    List<ImageDto> getImagesForProperty(Integer id);

    void updateById(Integer id, MultipartFile file);

    void deleteById(Integer id);
}
