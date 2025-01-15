package senla.model;

import java.io.Serializable;

public interface Identifiable<ID extends Serializable> {
    ID getId();
}
