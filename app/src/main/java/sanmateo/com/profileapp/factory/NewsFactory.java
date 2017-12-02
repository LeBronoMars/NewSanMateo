package sanmateo.com.profileapp.factory;

import org.fluttercode.datafactory.impl.DataFactory;

import sanmateo.com.profileapp.api.news.NewsDto;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class NewsFactory {

    private static final DataFactory FACTORY = new DataFactory();

    public static NewsDto dto() {
        NewsDto newsDto = new NewsDto();

        newsDto.body = FACTORY.getRandomChars(100);
        newsDto.createdAt = FACTORY.getBirthDate().toString();
        newsDto.deletedAt = FACTORY.getBirthDate().toString();
        newsDto.id = FACTORY.getNumberBetween(0, 1000);
        newsDto.imageUrl = FACTORY.getRandomChars(32);
        newsDto.reportedBy = FACTORY.getFirstName();
        newsDto.status = FACTORY.getRandomChars(4);
        newsDto.sourceUrl = FACTORY.getRandomChars(32);
        newsDto.title = FACTORY.getRandomWord();
        newsDto.updatedAt = FACTORY.getBirthDate().toString();
        return newsDto;
    }
}
