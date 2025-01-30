package senla.dao.impl;

import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.PropertyParameterDao;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;

@Repository
public class PropertyParameterDaoImpl extends AbstractDao<PropertyParameter, PropertyParameterId> implements PropertyParameterDao {
    @Override
    protected Class<PropertyParameter> getEntityClass() {
        return PropertyParameter.class;
    }
}
