package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.ImageDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Image;
import senla.model.Property;
import senla.repository.ImageRepository;
import senla.repository.PropertyRepository;
import senla.service.ImageService;
import senla.util.mappers.ImageMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final PropertyRepository propertyRepository;

    private final ImageMapper imageMapper;

    @Transactional
    @Override
    public ImageDto create(ImageDto imageDto) {
        Property property = propertyRepository.findById(imageDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, imageDto.getPropertyId()));

        Image image = imageMapper.toEntity(imageDto, property);
        ImageDto createdImage = imageMapper.toDto(imageRepository.save(image));
        log.info("Изображение успешно добавлено с ID: {}", createdImage.getId());
        return createdImage;
    }

    @Override
    public ImageDto getById(Integer id) {
        ImageDto image = imageMapper.toDto(imageRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Изображение успешно получено с ID: {}", image.getId());
        return image;
    }

    @Override
    public List<ImageDto> getImagesForProperty(Integer id) {
        List<Image> images = imageRepository.findByPropertyId(id);
        List<ImageDto> imageDtos = images.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} изображений", imageDtos.size());
        return imageDtos;
    }

    @Transactional
    @Override
    public void updateById(Integer id, ImageDto imageDto) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        imageDto.setId(id);
        imageMapper.updateEntity(imageDto, image);
        imageRepository.save(image);
        log.info("Изображение с ID: {} успешно обновлено", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        imageRepository.delete(image);
        log.info("Изображение с ID: {} успешно удалено", id);
    }
}
