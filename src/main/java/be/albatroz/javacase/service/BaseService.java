package be.albatroz.javacase.service;

import be.albatroz.javacase.entity.BaseEntity;
import be.albatroz.javacase.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class BaseService<E extends BaseEntity, R extends BaseRepository<E>> {

    protected final Class<E> entityClass;
    protected final R repository;

    public E getOneBySpecifications(String id, Specification<E> specification) {
        try {
            return repository.getOne(specification).orElseThrow(() -> new EntityNotFoundException("Could not find " + repository.getDomainClass() + " - " + id));
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Could not find " + repository.getDomainClass() + " - " + id);
        }
    }

    public <S extends E> S refresh(S entity) {
        return repository.refresh(entity);
    }

//    public <S extends E> S getReference(Class<S> clazz, String id) {
//        return repository.getReference(clazz, id);
//    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public List<E> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public List<E> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    public void deleteInBatch(Iterable<E> iterable) {
        repository.deleteInBatch(iterable);
    }

    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    public <S extends E> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    public <S extends E> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    public <S extends E> S save(S s) {
        return repository.save(s);
    }

    public <S extends E> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    public <S extends E> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    public <S extends E> long count(Example<S> example) {
        return repository.count(example);
    }

    public <S extends E> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    public Optional<E> findOne(Specification<E> specification) {
        return repository.findOne(specification);
    }

    public List<E> findAll(Specification<E> specification) {
        return repository.findAll(specification);
    }

    public Long getCount(Specification<E> countSpec) {
        return repository.getCount(countSpec);
    }

    public Page<E> findAll(Specification<E> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    public List<E> findAll(Specification<E> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    public Page<E> findAll(Specification<E> specification, Specification<E> countSpecification, Pageable pageable) {
        return repository.findAll(specification, countSpecification, pageable);
    }


    public long count(Specification<E> specification) {
        return repository.count(specification);
    }

    public void flush() {
        repository.flush();
    }

    public E getOne(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Unable to find entity with id " + id));
    }

    public E getOneOrCreate(Long id) {
        return repository.findById(id)
                .orElse(BeanUtils.instantiateClass(entityClass));
    }

    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

    public boolean deleteById(Long id) {
        repository.deleteById(id);
        return true;
    }

    public void delete(E entity) {
        repository.delete(entity);
    }

    public void deleteAll(Iterable<? extends E> entities) {
        repository.deleteAll(entities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public <S extends E> List<S> saveAll(Iterable<S> iterable) {
        return repository.saveAll(iterable);
    }

    public <S extends E> S saveAndFlush(S s) {
        return repository.saveAndFlush(s);
    }

}
