package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Profile.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Profile_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Profile#firstname
	 **/
	public static volatile SingularAttribute<Profile, String> firstname;
	
	/**
	 * @see senla.model.Profile#phone
	 **/
	public static volatile SingularAttribute<Profile, String> phone;
	
	/**
	 * @see senla.model.Profile
	 **/
	public static volatile EntityType<Profile> class_;
	
	/**
	 * @see senla.model.Profile#user
	 **/
	public static volatile SingularAttribute<Profile, User> user;
	
	/**
	 * @see senla.model.Profile#email
	 **/
	public static volatile SingularAttribute<Profile, String> email;
	
	/**
	 * @see senla.model.Profile#lastname
	 **/
	public static volatile SingularAttribute<Profile, String> lastname;

	public static final String FIRSTNAME = "firstname";
	public static final String PHONE = "phone";
	public static final String USER = "user";
	public static final String EMAIL = "email";
	public static final String LASTNAME = "lastname";

}

