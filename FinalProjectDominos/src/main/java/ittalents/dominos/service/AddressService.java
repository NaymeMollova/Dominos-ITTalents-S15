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

@Service

public class AddressService extends AbstractService {
    @Autowired
    private AddressRepository addressRepository;

    public boolean isAddressValid(String address) {
        throwExceptionIfAddressInvalid(address);
        return true;
    }

    @Transactional
    public AddressInfoDTO saveAddress(int loggedId, String addressName) {
        isAddressValid(addressName);
        checkIfAddressNameExistsInProfile(addressName, loggedId);
        Address a = new Address();
        a.setAddressName(addressName);

        ((Address) a).setAddressName(addressName);
        a.setOwner(findLoggedUser(loggedId));
        addressRepository.save(a);
        return new AddressInfoDTO(a.getAddressName());
    }
//    @Transactional
//    public AddressInfoDTO saveAddress(String loggedId, Address addressName, String pathId) {
//        if (!loggedId.equals(pathId)){
//            throw new UnauthorizedException("You have to log is as user with id "+ pathId);
//        }
////        isAddressValid(addressName.getAddress());
////        checkIfAddressAlreadyExists(addressName.getAddress());
//        Address a = addressRepository.save(addressName);
//        return new AddressInfoDTO(a.getAddressName());
//    }

    public IllegalArgumentException throwExceptionIfAddressInvalid(String address) {
        // Check that the address string is not empty
        if (address == null || address.trim().isEmpty()) {
            throw new BadRequestException("Address cannot be empty");
        }

        // Check that the address contains at least one word or number separated from other characters by spaces or punctuation marks

        if (!address.matches(".*[a-zA-Z0-9]+.*")) {

            if (!address.matches(".[a-zA-Z0-9]+.")) {

                throw new BadRequestException("Address must contain at least one word or number");
            }

            // Check that the address is not too long, usually limited to 255 characters
            if (address.length() > 255) {
                throw new BadRequestException("Address is too long");
            }

        }
        return null;
    }

    public void checkIfAddressNameExistsInProfile(String address, int loggedId) {
        Optional<Address> existingAddress = addressRepository.findByAddressName(address);
        if (existingAddress.isPresent()) {
            if (existingAddress.get().getOwner().getId() == loggedId) {
                throw new BadRequestException("Address already exists in your profile");
            }
        }
    }

    public List<Address> getAllAddressesByOwner(int id) {
        return addressRepository.findAllByOwner(findLoggedUser(id));
    }

    public AddressInfoDTO deleteAddress(int loggedId, int addressId) {
        Optional<Address> existedAddress = addressRepository.findById(addressId);
        if (existedAddress.isEmpty()) {
            throw new BadRequestException("Address with id " + addressId + " doesn't exist");
        }
        Optional<User> owner = userRepository.findByAddressNames(existedAddress);

        if (owner.isPresent() && loggedId == owner.get().getId()) {
            throw new BadRequestException("Address with id " + addressId + " doesn't exist in your profile");
        }
        Address deletedAddress = addressRepository.getReferenceById(addressId);
        addressRepository.deleteById(addressId);
        return new AddressInfoDTO(deletedAddress.getAddressName());
    }

    public AddressInfoDTO editAddress(String addressName, int loggedId, int addressId) {
        Optional<Address> existedAddress = addressRepository.findById(addressId);
        if (existedAddress.isEmpty()) {
            throw new BadRequestException("Address with id " + addressId + " doesn't exist");
        }
        Optional<User> owner = userRepository.findByAddressNames(existedAddress);
        if (!owner.isPresent()) {
            throw new BadRequestException("Address not found");
        }
        if (loggedId != owner.get().getId()) {
            throw new BadRequestException("Address with id " + addressId + " doesn't exist in your profile");
        }
        Address address = existedAddress.get();
        address.setAddressName(addressName);
        Address updatedAddress = addressRepository.save(address);
        return new AddressInfoDTO(updatedAddress.getAddressName());
    }

}




