package user.models.converters;


import user.lib.User;
import user.models.entities.UserEntity;

public class UserConverter {

    public static User toDto(UserEntity entity) {
        User dto = new User();
        dto.setUserId(entity.getUserId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setUserFirstName(entity.getUserFirstName());
        dto.setUserSurname(entity.getUserSurname());

        return dto;

    }

    public static UserEntity toEntity(User dto){
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setUserFirstName(dto.getUserFirstName());
        entity.setUserSurname(dto.getUserSurname());

        return entity;

    }
}
