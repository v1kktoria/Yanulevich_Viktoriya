package senla.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "user-roles",
        attributeNodes = {
                @NamedAttributeNode(User_.ROLES)
        }
)
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Profile profile;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Property> properties = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "tenant")
    private List<Application> applications = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Favorite favorite;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Chat> chats;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
    }
}
