package com.cambio.cds.rest;

import com.cambio.cds.domain.CdsModelService;
import com.cambio.cds.rest.dto.CdsModelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(path = "/api/models")
public class CdsModelController {

    private final CdsModelService cdsModelService;

    public CdsModelController(CdsModelService cdsModelService) {
        this.cdsModelService = cdsModelService;
    }

    @PostMapping(value = "/upload" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public CdsModelResponse uploadFile(@RequestPart(value = "file") final MultipartFile multipartFile) {
        cdsModelService.uploadFile(multipartFile);
        final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
        return CdsModelResponse.builder().message(response).build();
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

    @DeleteMapping(value = "/delete/{modelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CdsModelResponse deleteCdsModel(@PathVariable String modelId) {
        cdsModelService.deleteCdsModel(modelId);
        final String response = "[File with model id " + modelId + "] deleted successfully.";
        return CdsModelResponse.builder().message(response).build();
    }

}
