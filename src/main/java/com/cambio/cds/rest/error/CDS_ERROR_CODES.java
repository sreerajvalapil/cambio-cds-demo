package com.cambio.cds.rest.error;

public enum CDS_ERROR_CODES {

    CDS_MODEL_AWS_UPLOAD_ERROR,
    CDS_MODEL_AWS_DOWNLOAD_ERROR,
    CDS_MODEL_AWS_DELETE_ERROR,
    CDS_MODEL_INVALID_FILE,
    CDS_MODEL_INVALID_SEARCH_FIELD;

    @Override
    public String toString() {
        return "SA_ERR_" + this.name();
    }
}
