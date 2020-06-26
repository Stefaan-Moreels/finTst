package be.albatroz.javacase.repository;

import be.albatroz.javacase.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


public class BaseRepositoryImpl<T extends BaseEntity>
        extends SimpleJpaRepository<T, Long>
        implements BaseRepository<T> {

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager mainEntityManager) {
        super(entityInformation, mainEntityManager);
        this.entityManager = mainEntityManager;
    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager mainEntityManager) {
        super(domainClass, mainEntityManager);
        this.entityManager = mainEntityManager;
    }

    @Override
    public <S extends T> S refresh(S entity) {
        entityManager.refresh(entity);
        return entity;
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        return findAll(specification, Sort.unsorted());
    }

    @Override
    public Optional<T> getOne(Specification<T> specification) {
        return Optional.of(getQuery(specification, Sort.unsorted()).getSingleResult());
    }

    @Override
    public List<T> findAll(Specification<T> specification, Sort sort) {
        return getQuery(specification, sort).getResultList();
    }

    @Override
    public Class<T> getDomainClass() {
        return super.getDomainClass();
    }

    @Override
    public Long getCount(Specification<T> countSpecification) {
        return getCountQuery(countSpecification, getDomainClass()).getSingleResult();
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Specification<T> countSpecification, Pageable pageable) {

        TypedQuery<T> query = getQuery(specification, pageable);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(
                query.getResultList(),
                pageable,
                getCountQuery(countSpecification, getDomainClass()).getSingleResult()
        );
    }


}
