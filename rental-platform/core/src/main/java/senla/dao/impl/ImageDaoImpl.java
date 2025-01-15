package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ImageDao;
import senla.model.Image;

public class ImageDaoImpl extends AbstractDao<Image, Integer> implements ImageDao {

    @Override
    protected Class<Image> getEntityClass() {
        return Image.class;
    }
}
