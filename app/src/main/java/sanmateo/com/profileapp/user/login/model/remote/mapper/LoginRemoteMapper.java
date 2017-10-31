package sanmateo.com.profileapp.user.login.model.remote.mapper;


import io.reactivex.Single;
import io.reactivex.functions.Function;
import sanmateo.com.profileapp.api.user.UserDto;
import sanmateo.com.profileapp.user.login.model.local.User;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public class LoginRemoteMapper implements Function<UserDto, Single<User>> {

    @Override
    public Single<User> apply(UserDto userDto) {
        return Single.fromCallable(() -> {
            User user = new User();

            user.address = userDto.address;
            user.createdAt = userDto.createdAt;
            user.email = userDto.email;
            user.firstName = userDto.firstName;
            user.gender = userDto.gender;
            user.id = userDto.id;
            user.lastName = userDto.lastName;
            user.picUrl = userDto.picUrl;
            user.status = userDto.status;
            user.token = userDto.token;
            user.updatedAt = userDto.updatedAt;
            user.userLevel = userDto.userLevel;

            return user;
        });
    }
}
