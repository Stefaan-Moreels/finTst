package be.albatroz.javacase.repository;

import be.albatroz.javacase.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity>
        extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    Page<E> findAll(Specification<E> specification, Specification<E> countSpecification, Pageable pageable);

    <S extends E> S refresh(S entity);

//    <S extends E> S getReference(Class<S> clazz, UUID id);

    Long getCount(Specification<E> countSpecification);

    Optional<E> getOne(Specification<E> specification);

    Class<E> getDomainClass();

}
