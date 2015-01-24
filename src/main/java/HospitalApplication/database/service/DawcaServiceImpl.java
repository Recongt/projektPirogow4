package HospitalApplication.database.service;

import HospitalApplication.database.dao.DawcaDao;
import HospitalApplication.database.model.Dawca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by GUCIA on 2014-11-24.
 */

@Service("dawcaService")
public class DawcaServiceImpl implements DawcaService {

    @Autowired
    DawcaDao dawcaDao;


    @Override
    @Transactional
    public void persistDawca(Dawca dawca) {
        dawcaDao.persistDawca(dawca);
    }

    @Override
    @Transactional
    public Dawca findDawcaById(int id) {
        return dawcaDao.findDawcaById(id);
    }

    @Override
    @Transactional
    public Dawca findDawcaByPesel(String pesel) { return dawcaDao.findDawcaByPesel(pesel); }

    @Override
    @Transactional
    public void updateDawca(Dawca dawca) {
        dawcaDao.updateDawca(dawca);
    }

    @Override
    @Transactional
    public void deleteDawca(Dawca dawca) {
        dawcaDao.deleteDawca(dawca);
    }

    @Override
    @Transactional
    public List<Dawca> list(){
        return dawcaDao.list();
    }

}
