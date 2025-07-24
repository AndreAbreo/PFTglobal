package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.UsuariosTelefono;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class UsuariosTelefonoBean implements UsuariosTelefonoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;


    @Override
    public void crearUsuariosTelefono(UsuariosTelefono usuariosTelefono) {
        em.persist(usuariosTelefono);
        em.flush();
    }

    @Override
    public void modificarUsuariosTelefono(UsuariosTelefono usuariosTelefono) {
        em.merge(usuariosTelefono);
        em.flush();
    }

    @Override
    public void obtenerUsuarioTelefono(Long id) {
        em.find(UsuariosTelefono.class, id);
    }


    @Override
    public List<UsuariosTelefono> obtenerusuariosTelefono() {
        return em.createQuery("SELECT UsuariosTelefono FROM UsuariosTelefono usuariosTelefono", UsuariosTelefono.class).getResultList();
    }

    @Override
    public void eliminarTelefono(UsuariosTelefono usuariosTelefono) {
        UsuariosTelefono telefono = em.merge(usuariosTelefono);
        em.remove(telefono);
    }
}

