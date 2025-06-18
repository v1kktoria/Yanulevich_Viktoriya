package senla.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "favorite-properties",
        attributeNodes = {
                @NamedAttributeNode(Favorite_.PROPERTY)
        }
)
@Table(name = "favorites")
public class Favorite extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "favorites_properties",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private List<Property> property = new ArrayList<>();
}
