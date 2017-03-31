package carmo.tiago.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import carmo.tiago.ui.LoginApp;

public class CarbsFacade {

	public static CarbsEntity getCarbsByName(String name) {
		return (CarbsEntity) LoginApp.getInstance().getEm().createNamedQuery(CarbsEntity.FIND_SPECIFIC_NAME)
				.setParameter(1, name).getSingleResult();
	}

	public static List<CarbsEntity> getAllCarbs() {
		TypedQuery<CarbsEntity> allUsers = LoginApp.getInstance().getEm().createNamedQuery(CarbsEntity.FIND_ALL,
				CarbsEntity.class);
		return allUsers.getResultList();
	}

}
