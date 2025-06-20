package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Favorite.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Favorite_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Favorite#property
	 **/
	public static volatile ListAttribute<Favorite, Property> property;
	
	/**
	 * @see senla.model.Favorite
	 **/
	public static volatile EntityType<Favorite> class_;
	
	/**
	 * @see senla.model.Favorite#user
	 **/
	public static volatile SingularAttribute<Favorite, User> user;

	public static final String GRAPH_FAVORITE_PROPERTIES = "favorite-properties";
	public static final String PROPERTY = "property";
	public static final String USER = "user";

}

