package sanmateo.com.profileapp.factory.user;

import org.fluttercode.datafactory.impl.DataFactory;

import sanmateo.com.profileapp.api.user.UserDto;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public class UserFactory {

    private static final DataFactory FACTORY = new DataFactory();

    public static UserDto userDto() {
        UserDto userDto = new UserDto();

        userDto.address = FACTORY.getAddress();
        userDto.createdAt = FACTORY.getBirthDate().toString();
        userDto.email = FACTORY.getEmailAddress();
        userDto.firstName = FACTORY.getFirstName();
        userDto.gender = FACTORY.getRandomWord();
        userDto.id = FACTORY.getNumberBetween(0, 100);
        userDto.lastName = FACTORY.getLastName();
        userDto.picUrl = FACTORY.getRandomChars(32);
        userDto.status = FACTORY.getRandomChars(6);
        userDto.token = FACTORY.getRandomChars(32);
        userDto.updatedAt = FACTORY.getBirthDate().toString();
        return userDto;
    }
}
