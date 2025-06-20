package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Image.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Image_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Image#imageUrl
	 **/
	public static volatile SingularAttribute<Image, String> imageUrl;
	
	/**
	 * @see senla.model.Image#property
	 **/
	public static volatile SingularAttribute<Image, Property> property;
	
	/**
	 * @see senla.model.Image
	 **/
	public static volatile EntityType<Image> class_;

	public static final String IMAGE_URL = "imageUrl";
	public static final String PROPERTY = "property";

}

