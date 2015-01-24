package HospitalApplication.database.service;

/**
 * Created by GUCIA on 2014-11-24.
 */
import HospitalApplication.database.model.Dawca;

import java.util.List;

public interface DawcaService {
    void persistDawca(Dawca dawca);

    Dawca findDawcaById(int id);

    Dawca findDawcaByPesel(String pesel);

    void updateDawca(Dawca dawca);

    void deleteDawca(Dawca dawca);

    public List<Dawca> list();
}
