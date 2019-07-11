package com.dw.health.eportal.mo;

import com.dw.health.eportal.entity.Navigation;
import com.dw.health.eportal.entity.dto.NavigationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 组装NavigationDTO
 * @Author: qjf
 * @Date: 2019/3/14
 */
@Mapper
public interface NavigationDTOMapper {
    NavigationDTOMapper navMapper = Mappers.getMapper(NavigationDTOMapper.class);
    @Mappings({
            @Mapping(source = "navigation.navId", target = "id"),
            @Mapping(source = "navigation.name", target = "text")
    })
    NavigationDTO toNavigationDTO(Navigation navigation);
    List<NavigationDTO> toNavigationDTOs(List<Navigation> navigations);
}
