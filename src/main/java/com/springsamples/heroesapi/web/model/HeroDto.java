package com.springsamples.heroesapi.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroDto implements Serializable {
    static final long serialVersionUID = 408801129527585250L;
    private UUID id;
    @NotBlank
    private String name;
}
