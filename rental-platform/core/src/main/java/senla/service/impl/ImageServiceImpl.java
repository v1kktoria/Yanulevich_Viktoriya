package senla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senla.dao.ImageDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Image;
import senla.service.ImageService;
import senla.util.TransactionManager;
import senla.util.validator.ImageValidator;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Image create(Image image) {
        return TransactionManager.executeInTransaction(() -> {
            ImageValidator.validate(image);
            return imageDao.save(image);
        });
    }

    @Override
    public Image getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return imageDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Image> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return imageDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Image image) {
        TransactionManager.executeInTransaction(() -> {
            image.setId(id);
            ImageValidator.validate(image);
            imageDao.update(image);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Image image = imageDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            imageDao.delete(image);
        });
    }
}
