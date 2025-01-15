package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ImageDao;
import senla.dicontainer.annotation.Component;
import senla.model.Image;

@Component
public class ImageDaoImpl extends AbstractDao<Image, Integer> implements ImageDao {

    @Override
    protected Class<Image> getEntityClass() {
        return Image.class;
    }
}
