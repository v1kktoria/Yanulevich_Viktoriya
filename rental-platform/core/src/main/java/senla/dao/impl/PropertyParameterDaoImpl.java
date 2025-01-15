package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.PropertyParameterDao;
import senla.dicontainer.annotation.Component;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;

@Component
public class PropertyParameterDaoImpl extends AbstractDao<PropertyParameter, PropertyParameterId> implements PropertyParameterDao {
    @Override
    protected Class<PropertyParameter> getEntityClass() {
        return PropertyParameter.class;
    }
}
