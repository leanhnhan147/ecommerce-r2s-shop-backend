package com.r2s.mockproject.rest;

import com.r2s.mockproject.constants.ResponseCode;
import com.r2s.mockproject.dto.CategoryDTOResponse;
import com.r2s.mockproject.entity.Category;
import com.r2s.mockproject.entity.User;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addCaregory(@RequestBody(required = true)Map<String, Object> newCategory){
        try{
            if(ObjectUtils.isEmpty(newCategory)
                || ObjectUtils.isEmpty(newCategory.get("name"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Category foundCategory = this.categoryService.findByName(newCategory.get("name").toString()).orElse(null);
            if(!ObjectUtils.isEmpty(foundCategory)){
                return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
                        ResponseCode.DATA_ALREADY_EXISTS.getMessage());
            }

            Category insertedCategory = categoryService.addCategory(newCategory);
//            return super.success(new CategoryDTOResponse(insertedCategory));
            return super.success(insertedCategory);
        }catch(Exception e){
            e.printStackTrace();
        }

        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id,
                                            @RequestBody(required = false) Map<String, Object> newCategory){
        try{
            if(ObjectUtils.isEmpty(newCategory)
                    || ObjectUtils.isEmpty(newCategory.get("name"))){
                return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
            }

            Category foundCategory = this.categoryService.findCategoryById(id);
            if (ObjectUtils.isEmpty(foundCategory)) {
                return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
            }

            Category updatedCategory = categoryService.updateCategory(id, newCategory);
//            return super.success(new CategoryDTOResponse(insertedCategory));
            return super.success(updatedCategory);
        }catch(Exception e){
            e.printStackTrace();
        }
        return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
    }
}
