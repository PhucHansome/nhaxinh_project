package com.cg.model.dto;

import com.cg.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TagDTO {
    private Long id;
    private String name;

    public Tag toTag(){
        return new Tag()
                .setId(id)
                .setName(name);
    }
}
