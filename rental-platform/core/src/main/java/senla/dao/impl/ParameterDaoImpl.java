package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ParameterDao;
import senla.model.Parameter;

public class ParameterDaoImpl extends AbstractDao<Parameter, Integer> implements ParameterDao {
    @Override
    protected Class<Parameter> getEntityClass() {
        return Parameter.class;
    }
}
