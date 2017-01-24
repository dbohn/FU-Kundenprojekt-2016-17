package de.fuberlin.kundenprojekt.friedrich.roles;

import de.fuberlin.kundenprojekt.friedrich.models.Project;
import de.fuberlin.kundenprojekt.friedrich.models.Role;
import de.fuberlin.kundenprojekt.friedrich.storage.Database;
import org.hibernate.Session;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Collection of persistence logic regarding roles.
 * @author Team Friedrich
 */
public class RoleRepository {

    /**
     * Get a list of all roles from the database.
     * @return the role list
     */
    @Produces
    @Named
    public List<Role> getRoles() {
        Session session = Database.getSession();

        TypedQuery<Role> roleQuery = session.createQuery("from Role", Role.class);
        List<Role> roles = roleQuery.getResultList();

        session.close();

        return roles;
    }

    /**
     * Persist a role.
     *
     * @param role The role to persist.
     */
    public void storeRole(Role role) {
        Session session = Database.getSession();

        session.beginTransaction();

        session.saveOrUpdate(role);

        session.getTransaction().commit();

        session.close();
    }

    /**
     * Get a Role from the database by id
     * @param roleId the id of the role
     * @return the role or null if none found
     */
    public Role getRoleById(String roleId) {
        Session session = Database.getSession();

        TypedQuery<Role> userQuery = session.createQuery("from Role where id=:role", Role.class)
                .setParameter("role", Integer.parseInt(roleId));

        Role role = userQuery.getSingleResult();

        session.close();

        return role;
    }
}
