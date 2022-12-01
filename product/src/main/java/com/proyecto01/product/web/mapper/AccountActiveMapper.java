package com.proyecto01.product.web.mapper;

import com.proyecto01.product.domain.AccountActive;
import com.proyecto01.product.web.model.AccountActiveModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface AccountActiveMapper {
    AccountActive modelToEntity (AccountActiveModel model);
    AccountActiveModel entityToModel (AccountActive event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget AccountActive entity, AccountActive updateEntity);

}
