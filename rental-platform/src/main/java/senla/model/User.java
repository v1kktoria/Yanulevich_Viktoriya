package senla.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private String keycloakId;;

    @Column(name = "username", unique = true)
    private String username;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Profile profile;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "tenant")
    private List<Application> applications = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Favorite favorite;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Chat> chats;
}
