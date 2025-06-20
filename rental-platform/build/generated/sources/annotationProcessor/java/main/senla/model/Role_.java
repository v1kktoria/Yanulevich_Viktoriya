package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Role_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Role#roleName
	 **/
	public static volatile SingularAttribute<Role, String> roleName;
	
	/**
	 * @see senla.model.Role#description
	 **/
	public static volatile SingularAttribute<Role, String> description;
	
	/**
	 * @see senla.model.Role
	 **/
	public static volatile EntityType<Role> class_;
	
	/**
	 * @see senla.model.Role#users
	 **/
	public static volatile ListAttribute<Role, User> users;

	public static final String ROLE_NAME = "roleName";
	public static final String DESCRIPTION = "description";
	public static final String USERS = "users";

}

