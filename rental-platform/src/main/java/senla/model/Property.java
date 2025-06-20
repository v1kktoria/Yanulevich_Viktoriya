package senla.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import senla.model.constant.PropertyType;

import java.time.LocalDateTime;
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
        name = "property-parameters-reviews-images",
        attributeNodes = {
                @NamedAttributeNode(Property_.PARAMETERS),
                @NamedAttributeNode(Property_.REVIEWS),
                @NamedAttributeNode(Property_.IMAGES)
        }
)
@Table(name = "properties")
public class Property extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @Column(name = "area")
    private double area;

    @Column(name = "price")
    private double price;

    @Column(name = "rooms")
    private int rooms;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable (
            name = "properties_parameters",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "parameter_id")
    )
    private List<Parameter> parameters = new ArrayList<>();

    @OneToOne(mappedBy = "property", cascade = CascadeType.REMOVE)
    private Address address;

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private List<Application> applications = new ArrayList<>();

    @ManyToMany(mappedBy = "property")
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();
}
