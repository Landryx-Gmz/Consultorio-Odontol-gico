package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logica.Secretaio;
import persistencia.exceptions.NonexistentEntityException;

public class SecretaioJpaController implements Serializable {

    public SecretaioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public SecretaioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ConsultorioOdontologicoPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secretaio secretaio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(secretaio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secretaio secretaio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            secretaio = em.merge(secretaio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSecretaio(secretaio.getId()) == null) {
                throw new NonexistentEntityException("The secretaio with id " + secretaio.getId() + " no longer exists.");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretaio secretaio;
            try {
                secretaio = em.getReference(Secretaio.class, id);
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secretaio with id " + id + " no longer exists.", enfe);
            }
            em.remove(secretaio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secretaio> findSecretaioEntities() {
        return findSecretaioEntities(true, -1, -1);
    }

    public List<Secretaio> findSecretaioEntities(int maxResults, int firstResult) {
        return findSecretaioEntities(false, maxResults, firstResult);
    }

    private List<Secretaio> findSecretaioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secretaio.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Secretaio findSecretaio(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secretaio.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecretaioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secretaio> rt = cq.from(Secretaio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}

