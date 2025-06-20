package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Address.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Address_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Address#country
	 **/
	public static volatile SingularAttribute<Address, String> country;
	
	/**
	 * @see senla.model.Address#city
	 **/
	public static volatile SingularAttribute<Address, String> city;
	
	/**
	 * @see senla.model.Address#street
	 **/
	public static volatile SingularAttribute<Address, String> street;
	
	/**
	 * @see senla.model.Address#property
	 **/
	public static volatile SingularAttribute<Address, Property> property;
	
	/**
	 * @see senla.model.Address#houseNumber
	 **/
	public static volatile SingularAttribute<Address, String> houseNumber;
	
	/**
	 * @see senla.model.Address
	 **/
	public static volatile EntityType<Address> class_;

	public static final String COUNTRY = "country";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String PROPERTY = "property";
	public static final String HOUSE_NUMBER = "houseNumber";

}

