package com.proyecto01.product.web.mapper;

import com.proyecto01.product.domain.AccountPassive;
import com.proyecto01.product.domain.Client;
import com.proyecto01.product.web.model.AccountPassiveModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountPassiveMapper {
    AccountPassive modelToEntity (AccountPassiveModel model);
    AccountPassiveModel entityToModel (AccountPassive event);
    Client entityToModelClient (Client event);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget AccountPassive entity, AccountPassive updateEntity);

}
