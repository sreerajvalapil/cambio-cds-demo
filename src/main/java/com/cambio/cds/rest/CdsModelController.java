package com.cambio.cds.rest;

import com.cambio.cds.domain.CdsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/models")
public class CdsModelController {

    @Autowired
    private CdsModelService cdsModelService;

    @PostMapping(value= "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart(value= "file")
                                                 final MultipartFile multipartFile) {
        cdsModelService.uploadFile(multipartFile);
        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{modelId}")
    public ResponseEntity<ByteArrayResource> downloadCdsModel(@PathVariable String modelId) {
        final byte[] fileData = cdsModelService.downloadCdsModel(modelId);
        final ByteArrayResource byteArrayResource = new ByteArrayResource(fileData);
        return ResponseEntity
                .ok()
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header("Content-Disposition", "attachment; filename=" + modelId + ".txt")
                .body(byteArrayResource);
    }

    @DeleteMapping(value = "/delete/{modelId}")
    public ResponseEntity<String> deleteCdsModel(@PathVariable String modelId) {
        cdsModelService.deleteCdsModel(modelId);
        final String response = "[File with model id " + modelId + "] deleted successfully.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    }