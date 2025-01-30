package senla.dao.impl;

import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.ImageDao;
import senla.model.Image;

@Repository
public class ImageDaoImpl extends AbstractDao<Image, Integer> implements ImageDao {

    @Override
    protected Class<Image> getEntityClass() {
        return Image.class;
    }
}
