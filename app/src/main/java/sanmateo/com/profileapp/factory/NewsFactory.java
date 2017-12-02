package sanmateo.com.profileapp.factory;

import org.fluttercode.datafactory.impl.DataFactory;

import sanmateo.com.profileapp.api.news.NewsDto;
import sanmateo.com.profileapp.util.TimeUtils;


import static sanmateo.com.profileapp.util.TimeUtils.toDate;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class NewsFactory {

    private static final DataFactory FACTORY = new DataFactory();

    public static NewsDto dto() {
        NewsDto newsDto = new NewsDto();

        newsDto.body = FACTORY.getRandomChars(100);
        newsDto.createdAt = toDate(FACTORY.getBirthDate().getTime());
        newsDto.deletedAt = toDate(FACTORY.getBirthDate().getTime());
        newsDto.id = FACTORY.getNumberBetween(0, 1000);
        newsDto.imageUrl = FACTORY.getRandomChars(32);
        newsDto.reportedBy = FACTORY.getFirstName();
        newsDto.status = FACTORY.getRandomChars(4);
        newsDto.sourceUrl = FACTORY.getRandomChars(32);
        newsDto.title = FACTORY.getRandomWord();
        newsDto.updatedAt = toDate(FACTORY.getBirthDate().getTime());
        return newsDto;
    }

    public static NewsDto[] dtos() {
        NewsDto[] newsDtos = new NewsDto[FACTORY.getNumberBetween(1, 100)];

        for (int i = 0; i < newsDtos.length; i++) {
            newsDtos[i] = dto();
        }

        return newsDtos;
    }
}
