package senla.service.impl;

import senla.dao.impl.ImageDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Image;
import senla.service.ImageService;

import java.util.List;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDAOImpl imageDAO;

    @Override
    public Image create(Image image) {
        validate(image);
        return imageDAO.create(image);
    }

    @Override
    public Image getById(Integer id) {
        return imageDAO.getByParam(id);
    }

    @Override
    public List<Image> getAll() {
        return imageDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Image image) {
        validate(image);
        imageDAO.updateById(id, image);
    }

    @Override
    public void deleteById(Integer id) {
        imageDAO.deleteById(id);
    }

    private void validate(Image image) {
        if (image.getFilepath().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Путь файла не может быть пустым");
        }
    }
}
