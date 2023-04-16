package ittalents.dominos.service;

import ittalents.dominos.model.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AddressService extends AbstractService{
    @Autowired
    private AddressRepository addressRepository;
}
