package com.habsida.moragoproject.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileInput {

    private String originalTitle;
    private String path;
    private String type;
}
