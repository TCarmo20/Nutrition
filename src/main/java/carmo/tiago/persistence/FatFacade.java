package carmo.tiago.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import carmo.tiago.ui.LoginApp;

public class FatFacade {

	public static FatEntity getFatByName(String name) {
		return (FatEntity) LoginApp.getInstance().getEm().createNamedQuery(FatEntity.FIND_SPECIFIC_NAME)
				.setParameter(1, name).getSingleResult();
	}

	public static List<FatEntity> getAllFat() {
		TypedQuery<FatEntity> allUsers = LoginApp.getInstance().getEm().createNamedQuery(FatEntity.FIND_ALL,
				FatEntity.class);
		return allUsers.getResultList();
	}

}
