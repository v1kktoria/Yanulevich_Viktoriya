package senla.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import senla.model.constant.PropertyType;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Property {
    private int id;
    private User owner;
    private PropertyType type;
    private double area;
    private double price;
    private int rooms;
    private String description;
    private LocalDateTime createdAt;
    private boolean deleted;
}
