package com.onee.gestionportefeuilles.web;

import com.onee.gestionportefeuilles.dao.PieceJointeRepository;
import com.onee.gestionportefeuilles.entities.PieceJointe;
import com.onee.gestionportefeuilles.entities.Projet;
import com.onee.gestionportefeuilles.entities.Ressource;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@CrossOrigin("*")
@RestController
public class PieceJointeController {
    @Autowired
    PieceJointeRepository pieceJointeRepository;

//    @GetMapping(path="/pieceJointe/{id}",produces=
//            {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.APPLICATION_OCTET_STREAM_VALUE})
//    public byte[] getPieceJointe(@PathVariable("id") Long id) throws Exception{
//        PieceJointe p=pieceJointeRepository.findById(id).get();
//        if(p.getNom()==null)
//            return null;
//        else
//            return Files.readAllBytes(Paths.get(System.getProperty("user.home")+
//                    "\\Documents\\Stage Portefeuilles de Projets\\piecesJointes\\"+p.getNom()));
//    }
    @DeleteMapping("delete-piece-jointe/{id}")
    public void deletePieceJointe(@PathVariable Long id){
        PieceJointe p=pieceJointeRepository.findById(id).get();
        pieceJointeRepository.delete(p);
    }
    @GetMapping(path="/pieceJointe/{id}",produces=
            {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<Resource> getPieceJointe2(@PathVariable("id") Long id) throws Exception{
        PieceJointe p=pieceJointeRepository.findById(id).get();
        if(p.getNom()==null)
            return null;
        else
        {
            String directory=System.getProperty("user.home")+
                    "\\Documents\\Stage Portefeuilles de Projets\\piecesJointes\\";
            Path filePath=Paths.get(directory).toAbsolutePath().normalize().resolve(p.getNom());
            if(!Files.exists(filePath))
            {
                throw new FileNotFoundException(p.getNom()+" was not found on the server");
            }
            Resource resource= (Resource) new UrlResource(filePath.toUri());
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.add("File-Name",p.getNom());
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION,"attachement;File-Name="+p.getNom());
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath))).
                    headers(httpHeaders).body(resource);

        }

    }
}
