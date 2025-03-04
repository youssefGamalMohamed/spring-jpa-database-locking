package com.youssef_gamal.database_locking.mappers;


import com.youssef_gamal.database_locking.dtos.ProductDto;
import com.youssef_gamal.database_locking.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto productDto);
}
