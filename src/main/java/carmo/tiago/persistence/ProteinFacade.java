package carmo.tiago.persistence;

import java.util.List;
import javax.persistence.TypedQuery;
import carmo.tiago.ui.LoginApp;

public class ProteinFacade {

	public static ProteinEntity getProteinByName(String name) {
		return (ProteinEntity) LoginApp.getInstance().getEm().createNamedQuery(ProteinEntity.FIND_SPECIFIC_NAME)
				.setParameter(1, name).getSingleResult();
	}

	public static List<ProteinEntity> getAllProtein() {
		TypedQuery<ProteinEntity> allUsers = LoginApp.getInstance().getEm().createNamedQuery(ProteinEntity.FIND_ALL,
				ProteinEntity.class);
		return allUsers.getResultList();
	}

}
