package sanmateo.com.profileapp.incident.usecase.remote.mapper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import sanmateo.com.profileapp.api.incident.IncidentDto;
import sanmateo.com.profileapp.incident.usecase.Incident;

/**
 * Created by rsbulanon on 03/12/2017.
 */

public class DtoToIncidentMapper implements Function<IncidentDto, Single<Incident>> {

    @Override
    public Single<Incident> apply(IncidentDto dto) throws Exception {
        Incident incident = new Incident();

        incident.images = dto.images;
        incident.incidentId = dto.incidentId;
        incident.incidentDateReported = dto.incidentDateReported;
        incident.incidentDateUpdated = dto.incidentDateUpdated;
        incident.incidentAddress = dto.incidentAddress;
        incident.incidentDescription = dto.incidentDescription;
        incident.incidentStatus = dto.incidentStatus;
        incident.incidentType = dto.incidentType;
        incident.latitude = dto.latitude;
        incident.longitude = dto.longitude;
        incident.remarks = dto.remarks;
        incident.reporterId = dto.reporterId;
        incident.reporterName = dto.reporterName;
        incident.reporterContactNo = dto.reporterContactNo;
        incident.reporterEmail = dto.reporterEmail;
        incident.reportedAddress = dto.reportedAddress;
        incident.reporterPicUrl = dto.reporterPicUrl;
        incident.status = dto.status;
        return Single.just(incident);
    }
}
