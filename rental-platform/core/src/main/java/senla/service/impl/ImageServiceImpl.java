package senla.service.impl;

import senla.dao.impl.ImageDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Image;
import senla.service.ImageService;
import senla.util.TransactionManager;
import senla.util.validator.ImageValidator;

import java.util.List;
import java.util.Optional;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDAOImpl imageDAO;

    @Override
    public Optional<Image> create(Image image) {
        return TransactionManager.executeInTransaction(() -> {
            ImageValidator.validate(image);
            return Optional.ofNullable(imageDAO.save(image));
        });
    }

    @Override
    public Optional<Image> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(imageDAO.findById(id));
        });
    }

    @Override
    public List<Image> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return imageDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Image image) {
        TransactionManager.executeInTransaction(() -> {
            image.setId(id);
            ImageValidator.validate(image);
            imageDAO.update(image);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            imageDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
