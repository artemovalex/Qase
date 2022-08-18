package org.example.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Project {

    private String title;
    private String code;
    private String description;
}
