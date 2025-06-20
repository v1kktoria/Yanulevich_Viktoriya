package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import senla.model.constant.Status;

@StaticMetamodel(Application.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Application_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Application#owner
	 **/
	public static volatile SingularAttribute<Application, User> owner;
	
	/**
	 * @see senla.model.Application#createdAt
	 **/
	public static volatile SingularAttribute<Application, LocalDateTime> createdAt;
	
	/**
	 * @see senla.model.Application#property
	 **/
	public static volatile SingularAttribute<Application, Property> property;
	
	/**
	 * @see senla.model.Application#message
	 **/
	public static volatile SingularAttribute<Application, String> message;
	
	/**
	 * @see senla.model.Application
	 **/
	public static volatile EntityType<Application> class_;
	
	/**
	 * @see senla.model.Application#tenant
	 **/
	public static volatile SingularAttribute<Application, User> tenant;
	
	/**
	 * @see senla.model.Application#status
	 **/
	public static volatile SingularAttribute<Application, Status> status;

	public static final String OWNER = "owner";
	public static final String CREATED_AT = "createdAt";
	public static final String PROPERTY = "property";
	public static final String MESSAGE = "message";
	public static final String TENANT = "tenant";
	public static final String STATUS = "status";

}

