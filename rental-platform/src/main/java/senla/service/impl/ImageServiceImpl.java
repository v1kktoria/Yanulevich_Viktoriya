package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import senla.aop.MeasureExecutionTime;
import senla.service.MinioService;
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

    private final MinioService minioService;

    @Transactional
    @Override
    public ImageDto create(Integer propertyId, MultipartFile file) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, propertyId));

        String url = minioService.upload(file);
        Image image = Image.builder().property(property).imageUrl(url).build();
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
    public void updateById(Integer id, MultipartFile file) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        minioService.delete(image.getImageUrl());
        String newUrl = minioService.upload(file);
        image.setImageUrl(newUrl);

        imageRepository.save(image);
        log.info("Изображение с ID: {} успешно обновлено", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        minioService.delete(image.getImageUrl());
        imageRepository.delete(image);
        log.info("Изображение с ID: {} успешно удалено", id);
    }
}
