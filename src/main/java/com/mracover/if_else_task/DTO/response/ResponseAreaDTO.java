package com.mracover.if_else_task.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAreaDTO {

    private Long id;
    private String name;
    private List<ResponseAreaPointDTO> areaPoints;
}
