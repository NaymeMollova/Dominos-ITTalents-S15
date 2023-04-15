package ittalents.dominos.service;

import ittalents.dominos.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ModelMapper mapper;

}
