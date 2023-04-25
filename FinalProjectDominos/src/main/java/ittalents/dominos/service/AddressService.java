package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.AddressInfoDTO;
import ittalents.dominos.model.entities.Address;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService extends AbstractService {
    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public AddressInfoDTO addAddress(AddressInfoDTO addressInfoDTO) {
        Address address = new Address();
        User user = getUserById(addressInfoDTO.getUserId());
        System.out.println(user);
        if(addressRepository.existsByAddressName(addressInfoDTO.getAddressName())){
            throw new BadRequestException("Address already exist in your profile");
        }
        address.setAddressName(addressInfoDTO.getAddressName());
        address.setUser(user);
        addressRepository.save(address);
        return mapper.map(address, AddressInfoDTO.class);
    }

    public List<AddressInfoDTO> getAllAddressesByUser(int id) {
        return addressRepository.findAllByUser(getUserById(id))
                .stream()
                .map(address -> mapper.map(address, AddressInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAddress(int addressId) {
        Address address = getAddressById(addressId);
        addressRepository.deleteById(addressId);
    }

    @Transactional
    public AddressInfoDTO editAddress(String addressName, Integer id) {
        Address address = getAddressById(id);
        if(addressRepository.existsByAddressName(addressName)) {
            throw new BadRequestException("Address " + addressName +" is already exist");
        }
        address.setAddressName(addressName);
        addressRepository.save(address);
        return mapper.map(address, AddressInfoDTO.class);
    }

}




