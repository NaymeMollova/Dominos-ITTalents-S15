package ittalents.dominos.controller;

import ittalents.dominos.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController extends AbstractController {
    @Autowired
    private AddressService addressService;

    //ADD ADDRESS
//    @PostMapping("/dominos/addresses")
//    public AddressInfoDTO addAddress(HttpSession session, @RequestBody AddressInfoDTO newAddress) {
//        //if user loggedIn
//        try {
//            isAddressValid(newAddress.getAddress());
//        }
//
//        //if it doesn`t exist yet
//        //save it
//    }


    //EDIT ADDRESS
    //  @DeleteMapping("/dominos/addresses")

    //VIEW ADDRESSES
    //  @GetMapping("/dominos/addresses")


    //EDIT ADDRESSES
    //  @PutMapping("/dominos/addresses/{id}")


    private IllegalArgumentException isAddressValid(String address) {
        // Check that the address string is not empty
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        // Check that the address does not contain special characters such as @, #, $, etc.
        if (!address.matches("^[a-zA-Z0-9\\s\\-.,\\/]{1,255}$")) {
            throw new IllegalArgumentException("Address contains invalid characters");
        }

        // Check that the address does not contain HTML tags or other formatting
        if (address.matches(".*<(\"[^\"]*\"|'[^']*'|[^'\">])*>.*")) {
            throw new IllegalArgumentException("Address contains HTML tags or other formatting");
        }

        // Check that the address starts with a letter or number and does not end with a space
        if (!address.matches("^[a-zA-Z0-9].*[a-zA-Z0-9\\s\\-.,\\/]$")) {
            throw new IllegalArgumentException("Address starts with invalid characters or ends with a space");
        }

        // Check that the address contains at least one word or number separated from other characters by spaces or punctuation marks
        if (!address.matches(".*[a-zA-Z0-9]+.*")) {
            throw new IllegalArgumentException("Address must contain at least one word or number");
        }

        // Check that the address is not too short or too long, usually limited to 255 characters
        if (address.length() > 255) {
            throw new IllegalArgumentException("Address is too long");
        }
        return null;
    }

}
