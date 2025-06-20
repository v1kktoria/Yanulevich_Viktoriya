package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class User_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see senla.model.User#reviews
	 **/
	public static volatile ListAttribute<User, Review> reviews;
	
	/**
	 * @see senla.model.User#profile
	 **/
	public static volatile SingularAttribute<User, Profile> profile;
	
	/**
	 * @see senla.model.User#roles
	 **/
	public static volatile ListAttribute<User, Role> roles;
	
	/**
	 * @see senla.model.User#chats
	 **/
	public static volatile ListAttribute<User, Chat> chats;
	
	/**
	 * @see senla.model.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see senla.model.User#favorite
	 **/
	public static volatile SingularAttribute<User, Favorite> favorite;
	
	/**
	 * @see senla.model.User#properties
	 **/
	public static volatile ListAttribute<User, Property> properties;
	
	/**
	 * @see senla.model.User#username
	 **/
	public static volatile SingularAttribute<User, String> username;
	
	/**
	 * @see senla.model.User#applications
	 **/
	public static volatile ListAttribute<User, Application> applications;

	public static final String PASSWORD = "password";
	public static final String REVIEWS = "reviews";
	public static final String PROFILE = "profile";
	public static final String ROLES = "roles";
	public static final String CHATS = "chats";
	public static final String FAVORITE = "favorite";
	public static final String PROPERTIES = "properties";
	public static final String USERNAME = "username";
	public static final String APPLICATIONS = "applications";
	public static final String GRAPH_USER_ROLES = "user-roles";

}

