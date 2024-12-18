package senla.service.impl;

import senla.dao.impl.ImageDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Favorite;
import senla.model.Image;
import senla.service.ImageService;
import senla.util.validator.ImageValidator;

import java.util.List;
import java.util.Optional;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDAOImpl imageDAO;

    @Override
    public Optional<Image> create(Image image) {
        ImageValidator.validate(image);
        return Optional.ofNullable(imageDAO.create(image));
    }

    @Override
    public Optional<Image> getById(Integer id) {
        return Optional.ofNullable(imageDAO.getByParam(id));
    }

    @Override
    public List<Image> getAll() {
        return imageDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Image image) {
        ImageValidator.validate(image);
        imageDAO.updateById(id, image);
    }

    @Override
    public void deleteById(Integer id) {
        imageDAO.deleteById(id);
    }
}
