package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import senla.model.constant.PropertyType;

@StaticMetamodel(Property.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Property_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Property#owner
	 **/
	public static volatile SingularAttribute<Property, User> owner;
	
	/**
	 * @see senla.model.Property#area
	 **/
	public static volatile SingularAttribute<Property, Double> area;
	
	/**
	 * @see senla.model.Property#favorites
	 **/
	public static volatile ListAttribute<Property, Favorite> favorites;
	
	/**
	 * @see senla.model.Property#rooms
	 **/
	public static volatile SingularAttribute<Property, Integer> rooms;
	
	/**
	 * @see senla.model.Property#images
	 **/
	public static volatile ListAttribute<Property, Image> images;
	
	/**
	 * @see senla.model.Property#address
	 **/
	public static volatile SingularAttribute<Property, Address> address;
	
	/**
	 * @see senla.model.Property#rating
	 **/
	public static volatile SingularAttribute<Property, Double> rating;
	
	/**
	 * @see senla.model.Property#description
	 **/
	public static volatile SingularAttribute<Property, String> description;
	
	/**
	 * @see senla.model.Property#type
	 **/
	public static volatile SingularAttribute<Property, PropertyType> type;
	
	/**
	 * @see senla.model.Property#createdAt
	 **/
	public static volatile SingularAttribute<Property, LocalDateTime> createdAt;
	
	/**
	 * @see senla.model.Property#reviews
	 **/
	public static volatile ListAttribute<Property, Review> reviews;
	
	/**
	 * @see senla.model.Property#price
	 **/
	public static volatile SingularAttribute<Property, Double> price;
	
	/**
	 * @see senla.model.Property
	 **/
	public static volatile EntityType<Property> class_;
	
	/**
	 * @see senla.model.Property#parameters
	 **/
	public static volatile ListAttribute<Property, Parameter> parameters;
	
	/**
	 * @see senla.model.Property#applications
	 **/
	public static volatile ListAttribute<Property, Application> applications;

	public static final String OWNER = "owner";
	public static final String AREA = "area";
	public static final String FAVORITES = "favorites";
	public static final String GRAPH_PROPERTY_PARAMETERS_REVIEWS_IMAGES = "property-parameters-reviews-images";
	public static final String ROOMS = "rooms";
	public static final String IMAGES = "images";
	public static final String ADDRESS = "address";
	public static final String RATING = "rating";
	public static final String DESCRIPTION = "description";
	public static final String TYPE = "type";
	public static final String CREATED_AT = "createdAt";
	public static final String REVIEWS = "reviews";
	public static final String PRICE = "price";
	public static final String PARAMETERS = "parameters";
	public static final String APPLICATIONS = "applications";

}

