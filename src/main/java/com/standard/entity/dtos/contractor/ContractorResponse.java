package com.standard.entity.dtos.contractor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractorResponse {

    private Long id;
    private String name;
    private String contact;
    private String aadharNumber;
    private String profilePhoto;
}
