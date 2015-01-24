package HospitalApplication.database.dao;

/**
 * Created by GUCIA on 2014-11-24.
 */
import HospitalApplication.database.model.Dawca;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dawcaDao")
public class DawcaDaoImpl implements DawcaDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void persistDawca(Dawca dawca) {
        sessionFactory.getCurrentSession().persist(dawca);
    }

    @Override
    public Dawca findDawcaById(int id) {
        return (Dawca) sessionFactory.getCurrentSession().get(Dawca.class,id);
    }

    @Override
    public Dawca findDawcaByPesel(String pesel) {
        return (Dawca) sessionFactory.getCurrentSession().get(Dawca.class,pesel);
    }

    @Override
    public void updateDawca(Dawca dawca) {
        sessionFactory.getCurrentSession().update(dawca);
    }

    @Override
    public void deleteDawca(Dawca dawca) {
        sessionFactory.getCurrentSession().delete(dawca);
    }

    @Override
    public List<Dawca> list() {
        List<Dawca> dawcaList = sessionFactory.getCurrentSession().createQuery(" from Dawca").list();
        return dawcaList;
    }
}
