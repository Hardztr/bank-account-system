package dk.bankaccountsystem.persistence;

import dk.bankaccountsystem.persistence.entity.AccountEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class AccountDao {

    private final EntityManager em;

    @Inject
    public AccountDao(EntityManager em) {
        this.em = em;
    }

    public void persist(AccountEntity entity) {
        em.persist(entity);
    }

    public AccountEntity find(UUID accountNumber) {
        var entity = em.find(AccountEntity.class, accountNumber, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            throw new NotFoundException("Account not found");
        }
        return entity;
    }
}
