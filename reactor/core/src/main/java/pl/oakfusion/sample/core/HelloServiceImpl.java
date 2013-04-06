package pl.oakfusion.sample.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HelloServiceImpl implements HelloService {

	private final HelloRepository repository;

	@Autowired
	public HelloServiceImpl(HelloRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public List<HelloEntity> findAll() {
		return repository.findAll();
	}
}
