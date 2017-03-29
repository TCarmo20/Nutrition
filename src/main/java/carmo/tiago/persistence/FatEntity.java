package carmo.tiago.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FAT")
public class FatEntity extends FoodEntity {

	private static final long serialVersionUID = 1L;
	
}
