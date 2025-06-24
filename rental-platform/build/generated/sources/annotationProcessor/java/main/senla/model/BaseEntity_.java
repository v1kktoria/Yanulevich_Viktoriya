package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BaseEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class BaseEntity_ {

	
	/**
	 * @see senla.model.BaseEntity#id
	 **/
	public static volatile SingularAttribute<BaseEntity, Integer> id;
	
	/**
	 * @see senla.model.BaseEntity
	 **/
	public static volatile MappedSuperclassType<BaseEntity> class_;

	public static final String ID = "id";

}

