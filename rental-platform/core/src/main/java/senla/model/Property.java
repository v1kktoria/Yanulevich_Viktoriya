package senla.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import senla.model.constant.PropertyType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@NamedEntityGraph(
        name = "property-owner-address-images",
        attributeNodes = {
                @NamedAttributeNode(Property_.OWNER),
                @NamedAttributeNode(Property_.ADDRESS),
                @NamedAttributeNode(Property_.IMAGES)
        }
)
@Table(name = "properties")
public class Property extends Entity{
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "type")
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private PropertyType type;

    @Column(name = "area")
    private double area;

    @Column(name = "price")
    private double price;

    @Column(name = "rooms")
    private int rooms;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Set<PropertyParameter> propertyParameters = new HashSet<>();

    @OneToOne(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Address address;

    @OneToOne(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Analytics analytics;

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Set<Application> applications = new HashSet<>();

    @ManyToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Set<Review> reviews = new HashSet<>();

    public void loadLazyFields(){
        owner.getUsername();
        address.getCountry();
    }
}
