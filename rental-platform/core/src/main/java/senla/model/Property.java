package senla.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import senla.model.constant.PropertyType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "property-owner-address-images",
        attributeNodes = {
                @NamedAttributeNode(Property_.OWNER),
                @NamedAttributeNode(Property_.ADDRESS),
                @NamedAttributeNode(Property_.IMAGES)
        }
)
@Table(name = "properties")
public class Property extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "type")
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private PropertyType type;

    @Min(value = 0, message = "Площадь не может быть меньше нуля")
    @Column(name = "area")
    private double area;

    @Min(value = 0, message = "Цена не может быть меньше нуля")
    @Column(name = "price")
    private double price;

    @Min(value = 0, message = "Количество комнат не может быть меньше нуля")
    @Column(name = "rooms")
    private int rooms;

    @Size(max = 500, message = "Описание не должно превышать 500 символов")
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

    public void loadLazyFields() {
        owner.getUsername();
        if (address != null) {
            address.getCountry();
        }
    }
}
