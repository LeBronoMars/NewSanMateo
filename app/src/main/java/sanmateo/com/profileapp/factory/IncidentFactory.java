package sanmateo.com.profileapp.factory;

import org.fluttercode.datafactory.impl.DataFactory;

import sanmateo.com.profileapp.api.incident.IncidentDto;


import static sanmateo.com.profileapp.util.TimeUtils.toDate;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class IncidentFactory {


    private static final DataFactory FACTORY = new DataFactory();

    public static IncidentDto dto() {
        IncidentDto incidentDto = new IncidentDto();
        incidentDto.images = FACTORY.getRandomChars(32);
        incidentDto.incidentId = FACTORY.getNumberBetween(1, 1000);
        incidentDto.incidentDateReported = toDate(FACTORY.getBirthDate().getTime());;
        incidentDto.incidentDateUpdated = toDate(FACTORY.getBirthDate().getTime());;
        incidentDto.incidentAddress = FACTORY.getAddress();
        incidentDto.incidentDescription = FACTORY.getRandomText(50);
        incidentDto.incidentStatus = FACTORY.getRandomWord();
        incidentDto.incidentType = FACTORY.getRandomWord();
        incidentDto.latitude = FACTORY.getNumber();
        incidentDto.longitude = FACTORY.getNumber();
        incidentDto.remarks = FACTORY.getRandomWord();
        incidentDto.reporterId = FACTORY.getNumberBetween(1, 1000);
        incidentDto.reporterName = FACTORY.getFirstName();
        incidentDto.reporterContactNo = FACTORY.getRandomChars(11);
        incidentDto.reporterEmail = FACTORY.getEmailAddress();
        incidentDto.reportedAddress = FACTORY.getAddress();
        incidentDto.reporterPicUrl = FACTORY.getRandomChars(32);
        incidentDto.status = FACTORY.getRandomChars(5);

        return incidentDto;
    }
}
