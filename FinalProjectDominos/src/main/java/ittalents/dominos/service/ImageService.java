package ittalents.dominos.service;
import ittalents.dominos.model.DTOs.ProductDTO;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ImageService extends AbstractService {


    public ProductDTO uploadImageProduct(int productId, MultipartFile file){
        try{
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String name = UUID.randomUUID().toString() + "."+ext;
            File dir = new File("uploadsProducts");
            if(!dir.exists()){
                dir.mkdirs();
            }
            File f = new File(dir, name);
            Files.copy(file.getInputStream(), f.toPath());
            String url = dir.getName() + File.separator + f.getName();
            Product p = getProductById(productId);
            p.setImage(url);
            //System.out.println(url);
            productRepository.save(p);
            return mapper.map(p, ProductDTO.class);
        }
        catch (IOException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    public File download(String fileName) {
        File dir = new File("uploadsProducts");
        File f = new File(dir, fileName);
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }
}
