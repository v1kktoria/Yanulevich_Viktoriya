package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.ImageDao;
import senla.dao.PropertyDao;
import senla.dto.ImageDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Image;
import senla.model.Property;
import senla.service.ImageService;
import senla.util.mappers.ImageMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    private final PropertyDao propertyDao;

    private final ImageMapper imageMapper;

    @Override
    public ImageDto create(ImageDto imageDto) {
        Property property = propertyDao.findById(imageDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, imageDto.getPropertyId()));

        Image image = imageMapper.toEntity(imageDto, property);
        ImageDto createdImage = imageMapper.toDto(imageDao.save(image));
        log.info("Изображение успешно добавлено с ID: {}", createdImage.getId());
        return createdImage;
    }

    @Override
    public ImageDto getById(Integer id) {
        ImageDto image = imageMapper.toDto(imageDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Изображение успешно получено с ID: {}", image.getId());
        return image;
    }

    @Override
    public List<ImageDto> getAll() {
        List<Image> images = imageDao.findAll();
        List<ImageDto> imageDtos = images.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} изображений", imageDtos.size());
        return imageDtos;
    }

    @Override
    public void updateById(Integer id, ImageDto imageDto) {
        Image image = imageDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        imageDto.setId(id);
        imageMapper.updateEntity(imageDto, image);
        imageDao.update(image);
        log.info("Изображение с ID: {} успешно обновлено", id);
    }

    @Override
    public void deleteById(Integer id) {
        Image image = imageDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        imageDao.delete(image);
        log.info("Изображение с ID: {} успешно удалено", id);
    }
}
