package sanmateo.com.profileapp.user.local.mapper;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import sanmateo.com.profileapp.dao.UserEntity;
import sanmateo.com.profileapp.user.login.model.User;

/**
 * Created by rsbulanon on 26/11/2017.
 */

public class EntityToUserMapper implements MaybeTransformer<UserEntity, User> {

    @Override
    public MaybeSource<User> apply(Maybe<UserEntity> upstream) {
        return upstream.flatMap(userEntity -> {
            User user = new User();
            user.address = userEntity.address;
            user.address = userEntity.address;
            user.createdAt = userEntity.createdAt;
            user.email = userEntity.email;
            user.firstName = userEntity.firstName;
            user.gender = userEntity.gender;
            user.id = userEntity.id;
            user.lastName = userEntity.lastName;
            user.picUrl = userEntity.picUrl;
            user.status = userEntity.status;
            user.token = userEntity.token;
            user.updatedAt = userEntity.updatedAt;
            user.userLevel = userEntity.userLevel;
            return Maybe.just(user);
        });
    }
}
