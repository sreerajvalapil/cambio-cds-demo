package com.cambio.cds.domain;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.cambio.cds.persistence.entity.CdsModelDocument;
import com.cambio.cds.persistence.entity.CdsModelKeyword;
import com.cambio.cds.persistence.repository.CdsModelRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class CdsModelService {

    private final AmazonS3 s3client;

    private final CdsModelRepository cdsModelRepository;

    @Value("${awss3.bucketName}")
    private String bucketName;

    public CdsModelService(AmazonS3 s3client, CdsModelRepository cdsModelRepository) {
        this.s3client = s3client;
        this.cdsModelRepository = cdsModelRepository;
    }

    public void uploadFile(MultipartFile multipartFile) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            CdsModelDocument cdsModelDoc = jsonParseAndConvertCdsModelDoc(file);
            s3client.putObject(bucketName, cdsModelDoc.getModelId(), file);
            file.delete();
            cdsModelRepository.save(cdsModelDoc);
        } catch (final AmazonServiceException ex) {
            log.error("Error= {} while uploading file.", ex.getMessage());
            throw new IllegalArgumentException(ex);
        }

    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            log.error("Error converting the multi-part file to file= ", ex.getMessage());
            throw new IllegalArgumentException(ex);
        }
        return file;
    }

    public byte[] downloadCdsModel(String cdsModelId) {
        byte[] content;
        S3Object s3Object = s3client.getObject(bucketName, cdsModelId);
        if (s3Object == null) {
            throw new IllegalArgumentException("File down not exist in AWS s3 " + cdsModelId);
        }
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(inputStream);
        } catch (IOException ex) {
            log.error("Error downloading the the file= ", ex.getMessage());
            throw new IllegalArgumentException(ex);
        }
        return content;
    }

    public void deleteCdsModel(String cdsModelId) {
        try {
            s3client.deleteObject(bucketName, cdsModelId);
            cdsModelRepository.deleteById(cdsModelId);
        } catch (final AmazonServiceException ex) {
            log.error("Error= {} while deleting file.", ex.getMessage());
            throw new IllegalArgumentException(ex);
        }
    }

    private CdsModelDocument jsonParseAndConvertCdsModelDoc(File file) {
        String cdsModelId = "";
        List<CdsModelKeyword> modelKeywordsList = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;
            cdsModelId = (String) jsonObject.get("id");
            JSONObject descriptionList = (JSONObject) jsonObject.get("description");
            if (descriptionList != null) {
                JSONObject deails = (JSONObject) descriptionList.get("details");
                if (deails != null) {
                    Collection<JSONObject> detailsList = deails.values();
                    for (JSONObject languageDetails : detailsList) {
                        String languageId = (String) languageDetails.get("id");
                        JSONArray keywords = (JSONArray) languageDetails.get("keywords");
                        Iterator<String> keywordIterator = keywords.iterator();
                        while (keywordIterator.hasNext()) {
                            modelKeywordsList.add(CdsModelKeyword.builder().language(languageId).
                                    keyword(keywordIterator.next()).build());
                        }
                    }
                }
            }

        } catch (IOException | ParseException ex) {
            log.error("Error parsing the Json document = ", ex.getMessage());
            throw new IllegalArgumentException(ex);
        }
        return CdsModelDocument.builder().modelId(cdsModelId)
                .keywords(modelKeywordsList).build();
    }


}
