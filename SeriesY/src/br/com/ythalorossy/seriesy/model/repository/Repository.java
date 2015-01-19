package br.com.ythalorossy.seriesy.model.repository;

import java.util.List;

public abstract class Repository<T> {
	
	public abstract T findById(Long id);
	
	public abstract List<T> findAll();

	public abstract T save(T t);

	public abstract T update(T t);
	
	public abstract void delete(T t);

}
