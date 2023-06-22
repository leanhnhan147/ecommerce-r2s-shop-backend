package com.r2s.mockproject.dto;

import com.r2s.mockproject.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTOResponse {

    private Long id;
    private String name;

    public CategoryDTOResponse(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
