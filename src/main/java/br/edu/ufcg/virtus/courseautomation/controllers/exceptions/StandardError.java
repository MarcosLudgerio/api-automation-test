package br.edu.ufcg.virtus.courseautomation.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StandardError {
    private static final long serialVersionUID = 1L;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(Long timestamp, Integer status, String error, String message, String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
