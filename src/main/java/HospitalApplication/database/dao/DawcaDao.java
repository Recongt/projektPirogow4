package HospitalApplication.database.dao;

import HospitalApplication.database.model.Dawca;

import java.util.List;

/**
 * Created by GUCIA on 2014-11-24.
 */
public interface DawcaDao {

        void persistDawca(Dawca dawca);

        Dawca findDawcaById(int id);

        Dawca findDawcaByPesel(String pesel);

        void updateDawca(Dawca dawca);

        void deleteDawca(Dawca dawca);

        public List<Dawca> list();

}
