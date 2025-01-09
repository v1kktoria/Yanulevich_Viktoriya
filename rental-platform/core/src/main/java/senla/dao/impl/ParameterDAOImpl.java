package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Parameter;

@Component
public class ParameterDAOImpl extends AbstractDAO<Parameter, Integer> {
    @Override
    protected Class<Parameter> getEntityClass() {
        return Parameter.class;
    }
}
