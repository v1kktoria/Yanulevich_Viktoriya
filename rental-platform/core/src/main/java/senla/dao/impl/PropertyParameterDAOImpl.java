package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.PropertyParameter;
import senla.model.id.PropertyParameterId;

@Component
public class PropertyParameterDAOImpl extends AbstractDAO<PropertyParameter, PropertyParameterId> {
    @Override
    protected Class<PropertyParameter> getEntityClass() {
        return PropertyParameter.class;
    }
}
