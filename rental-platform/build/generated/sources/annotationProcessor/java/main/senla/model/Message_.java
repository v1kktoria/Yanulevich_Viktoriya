package senla.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Message.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Message_ extends senla.model.BaseEntity_ {

	
	/**
	 * @see senla.model.Message#createdAt
	 **/
	public static volatile SingularAttribute<Message, LocalDateTime> createdAt;
	
	/**
	 * @see senla.model.Message#sender
	 **/
	public static volatile SingularAttribute<Message, User> sender;
	
	/**
	 * @see senla.model.Message#chat
	 **/
	public static volatile SingularAttribute<Message, Chat> chat;
	
	/**
	 * @see senla.model.Message
	 **/
	public static volatile EntityType<Message> class_;
	
	/**
	 * @see senla.model.Message#content
	 **/
	public static volatile SingularAttribute<Message, String> content;

	public static final String CREATED_AT = "createdAt";
	public static final String SENDER = "sender";
	public static final String CHAT = "chat";
	public static final String CONTENT = "content";

}

