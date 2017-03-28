package carmo.tiago.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import carmo.tiago.ui.LoginApp;

public class ProteinFacade {

	public static void addProtein(ProteinEntity protein) {
		LoginApp.getInstance().getEm().getTransaction().begin();
		LoginApp.getInstance().getEm().persist(protein);
		LoginApp.getInstance().getEm().getTransaction().commit();
	}

	public static ProteinEntity getProteinByName(String name) {
		return (ProteinEntity) LoginApp.getInstance().getEm().createNamedQuery(ProteinEntity.FIND_SPECIFIC_NAME)
				.setParameter(1, name).getSingleResult();
	}

	public static List<ProteinEntity> getAllProtein() {
		TypedQuery<ProteinEntity> allProtein = LoginApp.getInstance().getEm().createNamedQuery(ProteinEntity.FIND_ALL,
				ProteinEntity.class);
		return allProtein.getResultList();
	}

}
