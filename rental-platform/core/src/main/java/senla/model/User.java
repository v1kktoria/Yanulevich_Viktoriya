package senla.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@NamedEntityGraph(
        name = "user-roles-properties-applications",
        attributeNodes = {
                @NamedAttributeNode(User_.ROLES),
                @NamedAttributeNode(User_.PROPERTIES),
                @NamedAttributeNode(User_.APPLICATIONS)
        }
)
@Table(name = "users")
public class User extends Entity{
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Profile profile;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Property> properties = new HashSet<>();

    @ManyToMany(mappedBy = "users", cascade = CascadeType.REMOVE)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "tenant")
    private Set<Application> applications = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Favorite favorite;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Review> reviews = new HashSet<>();
}
