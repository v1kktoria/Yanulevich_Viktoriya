package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Review.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Review_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Review#createdAt
	 **/
	public static volatile SingularAttribute<Review, LocalDateTime> createdAt;
	
	/**
	 * @see senla.model.Review#property
	 **/
	public static volatile SingularAttribute<Review, Property> property;
	
	/**
	 * @see senla.model.Review#rating
	 **/
	public static volatile SingularAttribute<Review, Integer> rating;
	
	/**
	 * @see senla.model.Review#comment
	 **/
	public static volatile SingularAttribute<Review, String> comment;
	
	/**
	 * @see senla.model.Review
	 **/
	public static volatile EntityType<Review> class_;
	
	/**
	 * @see senla.model.Review#user
	 **/
	public static volatile SingularAttribute<Review, User> user;

	public static final String CREATED_AT = "createdAt";
	public static final String PROPERTY = "property";
	public static final String RATING = "rating";
	public static final String COMMENT = "comment";
	public static final String USER = "user";

}

