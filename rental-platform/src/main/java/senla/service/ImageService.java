package senla.service;

import senla.model.Image;

import java.util.List;

public interface ImageService {

    Image create(Image image);

    Image getById(Integer id);

    List<Image> getAll();

    void updateById(Integer id, Image image);

    void deleteById(Integer id);
}
