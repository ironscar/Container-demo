package com.container.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExternalPropHolder {

    private String prop1;
    private String prop2;
    private String pass;
    
}
