package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.CategoryDTOResponse;
import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.repository.CategoryRepository;
import com.r2s.mockproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController extends BaseRestController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        try {
            List<Category> categories = this.categoryService.getAllCategory();
            List<CategoryDTOResponse> responses = categories.stream()
                    .map(category -> new CategoryDTOResponse(category))
                    .collect(Collectors.toList());

            return super.success(responses);
        }catch (Exception e){
            e.printStackTrace();
        }

        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("ADMIN")
    @PostMapping("")
    public ResponseEntity<?> addCaregory(@RequestBody(required = true)Map<String, Object> newCategory){
        try{

            if(ObjectUtils.isEmpty(newCategory)){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            if(ObjectUtils.isEmpty(newCategory.get("name"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Category foundCategory = this.categoryRepository.findByName(newCategory.get("name").toString()).orElse(null);
            if(!ObjectUtils.isEmpty(foundCategory)){
                return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(), ResponseCode.DATA_ALREADY_EXISTS.getMessage());
            }

            return super.success(new CategoryDTOResponse(this.categoryService.addCategory(newCategory)));
        }catch(Exception e){
            e.printStackTrace();
        }

        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}
