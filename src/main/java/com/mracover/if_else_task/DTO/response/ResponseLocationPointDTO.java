package com.mracover.if_else_task.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLocationPointDTO {

    private long id;
    private double latitude;
    private double longitude;
}
