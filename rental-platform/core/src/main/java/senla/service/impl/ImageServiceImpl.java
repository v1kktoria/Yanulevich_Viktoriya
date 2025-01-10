package senla.service.impl;

import senla.dao.ImageDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Image;
import senla.service.ImageService;
import senla.util.TransactionManager;
import senla.util.validator.ImageValidator;

import java.util.List;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

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
            return imageDao.findById(id);
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
            imageDao.deleteById(id);
        });
    }
}
