package senla.service;

import senla.dto.ImageDto;

import java.util.List;

public interface ImageService {

    ImageDto create(ImageDto imageDto);

    ImageDto getById(Integer id);

    List<ImageDto> getImagesForProperty(Integer id);

    void updateById(Integer id, ImageDto imageDto);

    void deleteById(Integer id);
}
