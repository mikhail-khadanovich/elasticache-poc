package com.vanguard.elasticcache.poc.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskDTO implements Serializable {

    public long calculatedResult;
}
