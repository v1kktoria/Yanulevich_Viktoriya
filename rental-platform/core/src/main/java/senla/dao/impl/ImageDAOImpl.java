package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Image;

@Component
public class ImageDAOImpl extends AbstractDAO<Image, Integer> {

    @Override
    protected Class<Image> getEntityClass() {
        return Image.class;
    }
}
