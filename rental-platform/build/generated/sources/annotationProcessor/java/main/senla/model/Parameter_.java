package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Parameter.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Parameter_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Parameter#name
	 **/
	public static volatile SingularAttribute<Parameter, String> name;
	
	/**
	 * @see senla.model.Parameter#description
	 **/
	public static volatile SingularAttribute<Parameter, String> description;
	
	/**
	 * @see senla.model.Parameter
	 **/
	public static volatile EntityType<Parameter> class_;
	
	/**
	 * @see senla.model.Parameter#properties
	 **/
	public static volatile ListAttribute<Parameter, Property> properties;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String PROPERTIES = "properties";

}

