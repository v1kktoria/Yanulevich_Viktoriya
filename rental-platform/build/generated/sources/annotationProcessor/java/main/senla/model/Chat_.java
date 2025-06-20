package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Chat.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Chat_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Chat#name
	 **/
	public static volatile SingularAttribute<Chat, String> name;
	
	/**
	 * @see senla.model.Chat#messages
	 **/
	public static volatile ListAttribute<Chat, Message> messages;
	
	/**
	 * @see senla.model.Chat
	 **/
	public static volatile EntityType<Chat> class_;
	
	/**
	 * @see senla.model.Chat#users
	 **/
	public static volatile ListAttribute<Chat, User> users;

	public static final String NAME = "name";
	public static final String MESSAGES = "messages";
	public static final String USERS = "users";

}

