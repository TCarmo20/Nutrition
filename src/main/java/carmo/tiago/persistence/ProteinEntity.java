package carmo.tiago.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROTEIN")
public class ProteinEntity extends FoodEntity {

	private static final long serialVersionUID = 1L;

}
